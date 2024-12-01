package org.cripsy.chatservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationDTO {
    private Integer conversationId;
    private Integer adminId;
    private Integer customerId;
    private List<MessageDTO> messages;
}
