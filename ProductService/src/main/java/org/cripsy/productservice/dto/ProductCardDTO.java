package org.cripsy.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCardDTO {
    private Integer productId;
    private String name;
    private double price;
    private double overallRatings;
    private int ratingCount;
    private boolean isAddedToFavorites;
    private List<String> imageUrls;
}
