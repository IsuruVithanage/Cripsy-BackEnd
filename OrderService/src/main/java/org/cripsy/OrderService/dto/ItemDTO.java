package org.cripsy.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private Integer productId;
    private Integer quantity;
    private Double price;
    private Double discount;
}
