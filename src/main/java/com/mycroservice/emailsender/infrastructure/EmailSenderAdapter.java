package com.mycroservice.emailsender.infrastructure;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderAdapter implements EmailSenderPort {
    private final JavaMailSender mailSender;

    public EmailSenderAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(MimeMessage message) {
        this.mailSender.send(message);
    }

    @Override
    public MimeMessage createMimeMessage() {
        return this.mailSender.createMimeMessage();
    }
}
