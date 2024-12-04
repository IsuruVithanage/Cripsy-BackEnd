package org.cripsy.customerservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String userName;
    private String email;
    private String password;
}
