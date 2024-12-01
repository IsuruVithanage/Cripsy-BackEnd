package org.cripsy.chatservice.controller;

import lombok.AllArgsConstructor;
import org.cripsy.chatservice.dto.ConversationDTO;
import org.cripsy.chatservice.service.ConversationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping("/create")
    public ConversationDTO createConversation(ConversationDTO conversationDTO){
        return conversationService.createConversation(conversationDTO);
    }

    @GetMapping("/{conversationId}")
    public ConversationDTO getConversationById(Integer conversationId){
        return conversationService.getConversationById(conversationId);
    }

    @GetMapping
    public List<ConversationDTO> getAllConversations(){
        return conversationService.getAllConversations();
    }

    @DeleteMapping("/delete/{conversationId}")
    public void deleteConversation(Integer conversationId){
        conversationService.deleteConversation(conversationId);
    }








}
