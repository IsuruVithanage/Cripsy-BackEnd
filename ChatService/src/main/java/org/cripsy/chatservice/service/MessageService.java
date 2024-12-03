package org.cripsy.chatservice.service;

import lombok.AllArgsConstructor;
import org.cripsy.chatservice.dto.MessageDTO;
import org.cripsy.chatservice.model.Message;
import org.cripsy.chatservice.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    private ModelMapper modelMapper;

    public MessageDTO createMessage(MessageDTO messageDTO){
        Message message = modelMapper.map(messageDTO, Message.class);
        Message savedMessage = messageRepository.save(message);
        return modelMapper.map(savedMessage, MessageDTO.class);
    }

    public MessageDTO getMessageById(Integer messageId){
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + messageId));
        return modelMapper.map(message, MessageDTO.class);
    }

    public List<MessageDTO> getMessagesByConversationId(Integer conversationId) {
        List<Message> messages = messageRepository.findByConversationId(conversationId);
        return messages.stream()
                .map(message -> modelMapper.map(message, MessageDTO.class))
                .collect(Collectors.toList());
    }
}
