package org.cripsy.customerservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ForgotPassword {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer passwordId;

    @Column(nullable = false)
    private Integer otp;

    @Column(nullable = false)
    private Date expirationTime;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
