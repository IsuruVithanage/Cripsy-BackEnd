package org.cripsy.chatservice.controller;

import lombok.AllArgsConstructor;
import org.cripsy.chatservice.dto.MessageDTO;
import org.cripsy.chatservice.service.MessageService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/messages")
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/create")
    public MessageDTO createMessage(@RequestBody MessageDTO messageDTO){
        MessageDTO createdMessage = messageService.createMessage(messageDTO);
        messagingTemplate.convertAndSend("/topic/conversation/" + messageDTO.getConversationId(), createdMessage);
        return createdMessage;
    }

    @GetMapping("/{messageId}")
    public MessageDTO getMessageById(@PathVariable Integer messageId){
        return messageService.getMessageById(messageId);
    }

    @GetMapping("/getAllMessages/{conversationId}")
    public List<MessageDTO> getAllMessages(@PathVariable Integer conversationId){
        return messageService.getMessagesByConversationId(conversationId);
    }
}
