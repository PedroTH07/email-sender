package com.mycroservice.emailsender.adapters.inbound;

import com.mycroservice.emailsender.application.EmailUseCase;
import com.mycroservice.emailsender.domain.dto.EmailRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/email")
public class EmailController {
    private final EmailUseCase service;

    public EmailController(EmailUseCase service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> sendEmail(@RequestBody EmailRequestDto data) {
        this.service.send(data);
        return ResponseEntity.ok().build();
    }
}
