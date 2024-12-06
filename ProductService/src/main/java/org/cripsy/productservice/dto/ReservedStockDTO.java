package org.cripsy.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservedStockDTO {
    private Integer productId;
    private Integer quantity;
    private Double price;
    private Double discount;
}
