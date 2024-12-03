package org.cripsy.productservice.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class WatchlistId implements Serializable {
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer userId;

    @SuppressWarnings("unused")
    public int getProduct(){
        return this.product.getProductId();
    }
}
