package org.cripsy.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer orderId;
    private Integer customerID;
    private LocalDate purchasedDate;
    private LocalDate deliveredDate;
    private String orderStatus;
    private Double totalPrice;
    private List<Integer> itemList; // Assuming itemList is a list of item IDs
    private Integer deliveryPersonId;

}
