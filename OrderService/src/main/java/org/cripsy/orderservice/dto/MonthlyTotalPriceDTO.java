package org.cripsy.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MonthlyTotalPriceDTO {
    private int year;
    private int month;
    private double totalPrice;
}
