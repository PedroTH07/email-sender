package com.mycroservice.emailsender.services;

import com.mycroservice.emailsender.persistence.EmailEntity;
import com.mycroservice.emailsender.persistence.EmailRepository;
import com.mycroservice.emailsender.dto.EmailRequestDto;
import com.mycroservice.emailsender.infrastructure.EmailSenderPort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService implements EmailUseCase {
    private final EmailSenderPort sender;
    private final EmailRepository repository;

    public EmailService(EmailSenderPort sender, EmailRepository repository) {
        this.sender = sender;
        this.repository = repository;
    }

    @Override
    public UUID send(EmailRequestDto email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pedro.mape7@gmail.com");
        message.setTo(email.to().toArray(new String[0]));
        message.setSubject(email.subject());
        message.setText(email.body());
        message.setSentDate(new Date());

        sender.send(message);
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
}
