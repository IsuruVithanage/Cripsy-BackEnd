package org.cripsy.chatservice.service;

import lombok.AllArgsConstructor;
import org.cripsy.chatservice.dto.ConversationDTO;
import org.cripsy.chatservice.model.Conversation;
import org.cripsy.chatservice.repository.ConversationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ConversationDTO createConversation(ConversationDTO conversationDTO){
        Conversation conversation = modelMapper.map(conversationDTO, Conversation.class);
        System.out.println(conversation.getAdminId());
        System.out.println(conversation.getCustomerId());
        Conversation savedConversation = conversationRepository.save(conversation);
        return modelMapper.map(savedConversation, ConversationDTO.class);
    }

    public ConversationDTO getConversationById(Integer conversationId){
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found with id: " + conversationId));
        return modelMapper.map(conversation, ConversationDTO.class);
    }

    public List<ConversationDTO> getAllConversations(){
        List<Conversation> conversationList= conversationRepository.findAll();
        return conversationList.stream()
                .map(conversation -> modelMapper.map(conversation, ConversationDTO.class))
                .toList();
    }

    public ConversationDTO findMessagesByCustomerId(Integer customerId){
        Conversation conversation = conversationRepository.findMessagesByCustomerId(customerId);
        return modelMapper.map(conversation, ConversationDTO.class);
    }

    public void deleteConversation(Integer conversationId){
        if (!conversationRepository.existsById(conversationId)) {
            throw new RuntimeException("Conversation not found with id: " + conversationId);
        }

        conversationRepository.deleteById(conversationId);
    }
}
