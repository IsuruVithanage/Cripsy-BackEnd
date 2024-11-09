package org.cripsy.emailservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
    private String body;

    @Schema(description = "specify that body is html or not (optional)")
    private boolean html;
}
