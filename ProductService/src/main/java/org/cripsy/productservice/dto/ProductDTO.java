package org.cripsy.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Integer productId;
    private String name;
    private String description;
    private Integer stock;
    private Double price;
    private Double discount;
    private Double rating;
    private Integer ratingCount;
    private List<String> imageUrls;
}
