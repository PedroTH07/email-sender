package com.mycroservice.emailsender.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record EmailRequestDto(
        @NotEmpty(message = "needs one or more recipients")
        List<String> to,

        @NotBlank(message = "needs a subject")
        String subject,

        String body){
}
