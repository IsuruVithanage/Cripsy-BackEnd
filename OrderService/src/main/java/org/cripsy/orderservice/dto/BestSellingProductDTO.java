package org.cripsy.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BestSellingProductDTO {
    private Integer productId;
    private Long totalQuantity;
    private Double totalPrice;
    private Double totalDiscountedPrice;
}
