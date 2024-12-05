package org.cripsy.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReservedStock {
    @Id
    @ManyToOne()
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @Id
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
}
