package com.mycroservice.emailsender.services;

import com.mycroservice.emailsender.dto.EmailRequestDto;
import com.mycroservice.emailsender.persistence.EmailEntity;

import java.util.UUID;

public interface EmailUseCase {
    UUID send(EmailRequestDto email);

    UUID save(EmailRequestDto email);

    EmailEntity getEmail(UUID id);
}
