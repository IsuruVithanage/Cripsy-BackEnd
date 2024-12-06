package org.cripsy.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDTO {
    private String name;
    private double price;
    private String description;
    private int stock;
    private double discount;
    private int category;
    private List<String> imageUrls;
}
