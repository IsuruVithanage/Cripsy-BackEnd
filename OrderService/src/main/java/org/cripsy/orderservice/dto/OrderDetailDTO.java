package org.cripsy.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private Integer orderId;
    private Integer deliveryPersonId;
    private Integer customerID;
    private LocalDate purchasedDate;
    private LocalDate deliveredDate;
    private String orderStatus;
    private Double totalPrice;
}
