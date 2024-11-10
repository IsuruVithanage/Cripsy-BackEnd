package org.cripsy.emailservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayloadDTO {
    @NotBlank(message = "receiverAddress must not be empty")
    @Email(message = "receiverAddress must be a valid Email Address")
    private String receiverAddress;

    @NotBlank(message = "subject must not be empty")
    private String subject;

    @NotBlank(message = "body must not be empty")
    @Schema(example = "String or <html>")
    private String body;
}
