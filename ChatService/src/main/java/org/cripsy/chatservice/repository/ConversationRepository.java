package org.cripsy.chatservice.repository;

import org.cripsy.chatservice.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {

    Conversation findMessagesByCustomerId(Integer customerId);

    Conversation findConversationByCustomerId(Integer customerId);
}
