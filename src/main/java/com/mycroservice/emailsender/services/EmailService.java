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
    public UUID send(EmailRequestDto email, List<MultipartFile> attachment) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("pedro.mape7@gmail.com");
            helper.setTo(email.to().toArray(new String[0]));
            helper.setSubject(email.subject());
            helper.setText(email.body(), true);
            helper.setSentDate(new Date());
            attachment.forEach(file -> addAttachment(file, helper));

            sender.send(message);
        } catch (MessagingException e) {
            return null;
        }

        return save(email);
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

    private void addAttachment(MultipartFile file, MimeMessageHelper helper) {
        try {
            String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
            helper.addAttachment(originalFilename, file);
        } catch (MessagingException | NullPointerException ignored) { }
    }
}
