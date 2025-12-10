package com.mycroservice.emailsender.adapters.inbound;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("health-check")
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<Map<String, String>> checkout() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", Instant.now().toString());

        return ResponseEntity.ok(response);
    }
}
