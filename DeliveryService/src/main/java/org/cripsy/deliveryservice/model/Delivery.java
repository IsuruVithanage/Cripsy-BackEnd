package org.cripsy.deliveryservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "deliveryPerson")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {
    @Id
    private Integer personId;
    private String name;
    private String email;
    private String password;
    private String contact;
    private  boolean availability;
    // new coment
}




