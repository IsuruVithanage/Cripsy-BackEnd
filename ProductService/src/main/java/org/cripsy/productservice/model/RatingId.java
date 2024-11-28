package org.cripsy.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RatingId implements Serializable {
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "\"user\"")
    private String user;

    @SuppressWarnings("unused")
    public int getProductId(){
        return this.product.getProductId();
    }
}
