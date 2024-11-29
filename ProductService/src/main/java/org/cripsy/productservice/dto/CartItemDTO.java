package org.cripsy.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Integer productId;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Long ratingCount;
    private Double avgRatings;
    private Integer quantity;
    private Double total;
    private String imageUrl;
}
