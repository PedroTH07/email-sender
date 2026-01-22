package com.mycroservice.emailsender.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, UUID> {
    @Query("SELECT COUNT(e) FROM EmailEntity e")
    long countEmailEntity();

    @Query("SELECT e.sentAt FROM EmailEntity e ORDER BY e.sentAt")
    Page<Instant> getFirstSent(Pageable pageable);
}
