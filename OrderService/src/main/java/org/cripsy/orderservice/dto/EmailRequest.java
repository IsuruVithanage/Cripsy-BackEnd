package org.cripsy.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    private String toEmail; // Recipient's email address
    private String subject; // Email subject
    private String body; // Email body
}
