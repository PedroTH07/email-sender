package com.mycroservice.emailsender.infrastructure;

import jakarta.mail.internet.MimeMessage;

public interface EmailSenderPort {
    void send(MimeMessage message);

    MimeMessage createMimeMessage();
}
