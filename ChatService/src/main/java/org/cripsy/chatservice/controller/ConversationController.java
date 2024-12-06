package org.cripsy.chatservice.controller;

import lombok.AllArgsConstructor;
import org.cripsy.chatservice.dto.ConversationDTO;
import org.cripsy.chatservice.dto.MessageDTO;
import org.cripsy.chatservice.service.ConversationService;
import org.cripsy.chatservice.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/conversations")
public class ConversationController {

    private final ConversationService conversationService;
    private final MessageService messageService;

    @PostMapping("/create")
    public ConversationDTO createConversation(@RequestBody ConversationDTO conversationDTO){
        return conversationService.createConversation(conversationDTO);
    }

    @GetMapping("/{conversationId}")
    public ConversationDTO getConversationById(@PathVariable Integer conversationId){
        return conversationService.getConversationById(conversationId);
    }

    @GetMapping
    public List<ConversationDTO> getAllConversations(){
        return conversationService.getAllConversations();
    }

    @GetMapping("/getConversation/{customerId}")
    public List<MessageDTO> findConversationByCustomerId(@PathVariable Integer customerId){
        ConversationDTO conversationDTO = conversationService.findMessagesByCustomerId(customerId);
        return messageService.getMessagesByConversationId(conversationDTO.getConversationId());
    }

    @DeleteMapping("/delete/{conversationId}")
    public void deleteConversation(@PathVariable Integer conversationId){
        conversationService.deleteConversation(conversationId);
    }


}
