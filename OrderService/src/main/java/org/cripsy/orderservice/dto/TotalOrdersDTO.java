package org.cripsy.orderservice.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalOrdersDTO {
   private Double percentageDifference;
   private  Integer thisMonthOrders;
   private Integer lastMonthOrders;
}
