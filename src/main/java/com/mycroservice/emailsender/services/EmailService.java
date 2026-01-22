package com.mycroservice.emailsender.services;

import com.mycroservice.emailsender.dto.DeliveryResponseDto;
import com.mycroservice.emailsender.persistence.EmailEntity;
import com.mycroservice.emailsender.persistence.EmailRepository;
import com.mycroservice.emailsender.dto.EmailRequestDto;
import com.mycroservice.emailsender.infrastructure.EmailSenderPort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
public class EmailService implements EmailUseCase {
    private final EmailSenderPort sender;
    private final EmailRepository repository;

    public EmailService(EmailSenderPort sender, EmailRepository repository) {
        this.sender = sender;
        this.repository = repository;
    }

    @Override
    public UUID send(EmailRequestDto email, MultipartFile html) {
        try {
            String body = this.validateBody(email.body(), html);

            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("pedro.mape7@gmail.com");
            helper.setTo(email.to().toArray(new String[0]));
            helper.setSubject(email.subject());
            helper.setText(body, true);
            helper.setSentDate(new Date());

            sender.send(message);
        } catch (MessagingException e) {
            return null;
        }

        return this.save(email);
    }

    @Override
    public UUID save(EmailRequestDto data) {
        var email = new EmailEntity();
        email.setTo(data.to());
        email.setSubject(data.subject());
        email.setSentAt(Instant.now());

        EmailEntity savedEmail = this.repository.save(email);
        return savedEmail.getEmailId();
    }

    @Override
    public EmailEntity getEmail(UUID id) {
        Optional<EmailEntity> email = repository.findById(id);
        return email.orElse(null);
    }

    @Override
    public List<EmailEntity> getAll() {
        return this.repository.findAll();
    }

    @Override
    public DeliveryResponseDto emailDelivery() {
        var firstSent = repository.getFirstSent(PageRequest.of(0, 1));
        if (firstSent.isEmpty()) {
            return new DeliveryResponseDto(0L, 0L, new BigDecimal("0.0"));
        }
        var days = Duration.between(firstSent.getContent().getFirst(), Instant.now()).toDays();
        if (days == 0) days = 1;
        var emailAmount = this.repository.countEmailEntity();

        var deliveryTax = BigDecimal
                .valueOf(emailAmount)
                .divide(BigDecimal.valueOf(days), RoundingMode.HALF_UP);

        return new DeliveryResponseDto(emailAmount, days, deliveryTax);
    }

    private String validateBody(String body, MultipartFile html) {
        try {
            return html != null
                    ? new String(html.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
                    : body;
        } catch (IOException e) {
            return "erro no servidor ao validar o arquivo";
        }
    }
}
