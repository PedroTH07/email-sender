package com.mycroservice.emailsender.application;

import com.mycroservice.emailsender.domain.Email;
import com.mycroservice.emailsender.domain.dto.EmailRequestDto;
import com.mycroservice.emailsender.ports.EmailSenderPort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class EmailService implements EmailUseCase {
    private final EmailSenderPort sender;

    public EmailService(EmailSenderPort sender) {
        this.sender = sender;
    }

    @Override
    public void send(EmailRequestDto data) {
        Email email = new Email(
                data.to(),
                data.subject(),
                data.body(),
                Instant.now(),
                null
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email.getFrom());
        message.setTo(email.getTo().toArray(new String[0]));
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        message.setSentDate(new Date());

        sender.send(message);
    }
}
