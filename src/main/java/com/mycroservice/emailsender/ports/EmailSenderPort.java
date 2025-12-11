package com.mycroservice.emailsender.ports;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderPort {
    void send(SimpleMailMessage message);
}
