package org.cripsy.orderservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDashbordDTO {
    private Double  lastMonthTotalPrice;
    private Double percentageDifference;
    private Double thisMonthTotalPrice;

}

