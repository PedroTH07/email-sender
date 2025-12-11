package com.mycroservice.emailsender.domain;

import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

public class Email {
    private final String from = "noreply@gmail.com";
    private final List<String> to;
    private final String subject;
    private final String body;
    private final Instant sentAt;
    private final List<MultipartFile> attachment;

    public Email(
            List<String> to,
            String subject,
            String body,
            Instant sentAt,
            List<MultipartFile> attachment) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.sentAt = sentAt;
        this.attachment = attachment;
    }

    public String getFrom() {
        return from;
    }

    public List<String> getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public String getBody() {
        return body;
    }

    public List<MultipartFile> getAttachment() {
        return attachment;
    }
}
