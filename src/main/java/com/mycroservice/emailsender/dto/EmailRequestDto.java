package com.mycroservice.emailsender.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

public record EmailRequestDto(
        @NotEmpty(message = "needs one or more recipients")
        List<String> to,

        @NotBlank(message = "needs a subject")
        String subject,

        @NotBlank(message = "needs a body")
        String body){
}
