package com.mycroservice.emailsender.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record EmailRequestDto(
        List<String> to,
        String subject,
        String body,
        List<MultipartFile> attachment) {
}
