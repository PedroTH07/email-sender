package com.mycroservice.emailsender.services;

import com.mycroservice.emailsender.persistence.EmailEntity;
import com.mycroservice.emailsender.persistence.EmailRepository;
import com.mycroservice.emailsender.dto.EmailRequestDto;
import com.mycroservice.emailsender.infrastructure.EmailSenderPort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

//    public DeliveryResponseDto emailDelivery() {
//        var emailAmount = this.repository.countEmailEntity();
//        var firstDay = this.repository.
//    }

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
