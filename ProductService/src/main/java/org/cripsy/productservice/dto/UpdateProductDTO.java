package org.cripsy.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDTO {
    private Integer productId;
    private String name;
    private double price;
    private String description;
    private int stock;
    private double discount;
    private List<String> imageUrls;
}
