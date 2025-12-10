package com.mycroservice.emailsender.domain;

import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

public class Email {
    private String from;
    private List<String> to;
    private String subject;
    private String body;
    private Instant sentAt;
    private List<MultipartFile> attachment;

    public Email(
            String from,
            List<String> to,
            String subject,
            String body,
            Instant sentAt,
            List<MultipartFile> attachment) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.sentAt = sentAt;
        this.attachment = attachment;
    }
}
