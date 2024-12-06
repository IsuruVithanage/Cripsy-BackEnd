package org.cripsy.orderservice.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalItemDTO {
    private Long percentageDifference;
    private  Integer thisMonthQuantity;
    private  Integer lastMonthQuantity;

}
