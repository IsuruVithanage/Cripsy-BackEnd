package org.cripsy.chatservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Integer messageId;
    private Integer conversationId;
    private String sender;
    private String message;
    private LocalDateTime dateTime;
}
