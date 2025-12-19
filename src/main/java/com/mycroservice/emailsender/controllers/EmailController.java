package com.mycroservice.emailsender.controllers;

import com.mycroservice.emailsender.persistence.EmailEntity;
import com.mycroservice.emailsender.services.EmailUseCase;
import com.mycroservice.emailsender.dto.EmailRequestDto;
import com.mycroservice.emailsender.dto.EmailResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/email")
public class EmailController {
    private final EmailUseCase service;

    public EmailController(EmailUseCase service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EmailResponseDto> sendEmail(
            @ModelAttribute @Valid EmailRequestDto data,
            @RequestPart(value = "attachment", required = false) List<MultipartFile> attachment) {
        String location = "v1/email/" + service.send(data, attachment);
        var response = new EmailResponseDto("sent", location, Instant.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("{emailId}")
    public ResponseEntity<EmailEntity> getEmail(@PathVariable UUID emailId) {
        EmailEntity response = service.getEmail(emailId);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

}
