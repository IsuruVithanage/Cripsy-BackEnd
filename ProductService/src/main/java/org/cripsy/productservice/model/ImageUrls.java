package org.cripsy.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageUrls {
    @Id
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    private String url;
}
