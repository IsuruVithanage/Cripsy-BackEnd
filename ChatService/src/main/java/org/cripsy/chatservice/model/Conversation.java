package org.cripsy.chatservice.model;

import jakarta.persistence.*;
import lombok.*;

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
    private Integer id;
    private Integer adminId;
    private Integer customerId;
}
