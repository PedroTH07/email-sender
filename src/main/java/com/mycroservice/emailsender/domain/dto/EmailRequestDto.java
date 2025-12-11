package com.mycroservice.emailsender.domain.dto;

import java.util.List;

public record EmailRequestDto(List<String> to, String subject, String body) {
}
