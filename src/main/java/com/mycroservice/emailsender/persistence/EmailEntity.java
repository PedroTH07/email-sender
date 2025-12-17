package com.mycroservice.emailsender.persistence;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TbEmail")
public class EmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID emailId;

    @ElementCollection
    @CollectionTable(name = "TbEmailRecipient",
    joinColumns = @JoinColumn(name = "emailId"))
    @Column(name = "recipient")
    private List<String> to;

    private String subject;
    private Instant sentAt;
    private final Instant timestamp = Instant.now();

    public EmailEntity() {
    }

    public EmailEntity(UUID emailId, List<String> to, String subject, Instant sentAt) {
        this.emailId = emailId;
        this.to = to;
        this.subject = subject;
        this.sentAt = sentAt;
    }

    public UUID getEmailId() {
        return emailId;
    }

    public List<String> getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    private Instant getSentAt() {
        return sentAt;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setSentAt(Instant sentAt) {
        this.sentAt = sentAt;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }
}
