package org.cripsy.chatservice.repository;

import org.cripsy.chatservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
