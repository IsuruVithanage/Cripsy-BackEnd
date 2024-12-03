package org.cripsy.customerservice.dto;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String body) {
}
