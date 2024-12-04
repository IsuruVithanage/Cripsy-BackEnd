package org.cripsy.emailservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.cripsy.emailservice.dto.PayloadDTO;
import org.cripsy.emailservice.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/email")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    @Operation(summary = "Send Email", description = "Send an email to given address", tags = "Admin")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody PayloadDTO payload){
        return emailService.sendEmail(payload);
    }
}
