package org.cripsy.chatservice.service;

import lombok.AllArgsConstructor;
import org.cripsy.chatservice.model.Conversation;
import org.cripsy.chatservice.repository.ConversationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Conversation createConversation(Conversation conversation){
        return conversationRepository.save(conversation);
    }

    public Conversation getConversationById(Integer conversationId){
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found with id: " + conversationId));
    }

    public List<Conversation> getAllConversations(){
        return conversationRepository.findAll();
    }

    public void deleteConversation(Integer conversationId){
        if (!conversationRepository.existsById(conversationId)) {
            throw new RuntimeException("Conversation not found with id: " + conversationId);
        }

        conversationRepository.deleteById(conversationId);
    }
}
