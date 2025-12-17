package com.mycroservice.emailsender.infrastructure;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderPort {
    void send(SimpleMailMessage message);
}
