package org.cripsy.exampleservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private String description;
    private double price;

}
