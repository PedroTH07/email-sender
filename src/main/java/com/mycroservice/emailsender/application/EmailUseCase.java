package com.mycroservice.emailsender.application;

import com.mycroservice.emailsender.domain.dto.EmailRequestDto;

public interface EmailUseCase {
    void send(EmailRequestDto email);
}
