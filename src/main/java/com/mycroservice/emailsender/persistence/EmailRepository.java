package com.mycroservice.emailsender.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, UUID> {
    @Query("SELECT COUNT(e) FROM EmailEntity e")
    long countEmailEntity();
}
