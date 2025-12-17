package com.mycroservice.emailsender.dto;

import java.time.Instant;

public record EmailResponseDto(
        String status,
        String location,
        Instant timestamp
) {
}
