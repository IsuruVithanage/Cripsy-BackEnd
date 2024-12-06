package org.cripsy.adminservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBestSellingDTO {
    private int productId;
    private Long totalQuantity;
    private double totalPrice;
    private double totalDiscountedPrice;
    private String name;
    private double avgRatings;
}