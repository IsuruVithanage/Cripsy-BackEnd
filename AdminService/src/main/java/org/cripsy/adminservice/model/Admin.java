package org.cripsy.adminservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "admin")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String contact;
}
