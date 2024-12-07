package org.cripsy.deliveryservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "deliveryPerson")
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer personId;
    private String name;
    private String email;
    private String password;
    private String contact;
    private  Boolean availability;

}




