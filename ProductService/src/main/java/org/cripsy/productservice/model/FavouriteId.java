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
public class FavouriteId  implements Serializable {
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer userId;


    @SuppressWarnings("unused")
    public Product getProduct(){
        Product product = this.product;
        product.setFavourites(null);
        product.setRatings(null);
        product.setCart(null);
        product.setImageUrls(null);
        return product;
    }
}
