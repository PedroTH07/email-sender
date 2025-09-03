package com.mycroservice.emailsender.domain;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public class Email {
    private String from;

    private List<String> to;

    private String subject;

    private String body;

    private LocalDateTime sentAt;

    private List<MultipartFile> attachment;

    public Email(
            String from, List<String> to, String subject, String body, LocalDateTime sentAt, List<MultipartFile> attachment
    ) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.sentAt = sentAt;
        this.attachment = attachment;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public List<MultipartFile> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<MultipartFile> attachment) {
        this.attachment = attachment;
    }
}
