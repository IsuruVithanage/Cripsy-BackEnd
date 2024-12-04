package org.cripsy.chatservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "conversation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer conversationId;
    private Integer adminId;
    private Integer customerId;
}
