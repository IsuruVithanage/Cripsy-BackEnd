package org.cripsy.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name="product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int productId;
    private String name;
    private String description;
    private double price;
    private int stock;
    private double discount;
    private double rating;
    private int ratingCount;
}
