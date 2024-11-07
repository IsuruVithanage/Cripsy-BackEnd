package org.cripsy.authenticationservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

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
    @JoinColumn(name = "user_id")
    private Users user;

}
