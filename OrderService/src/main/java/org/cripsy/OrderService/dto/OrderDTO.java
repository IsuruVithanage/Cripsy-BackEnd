package org.cripsy.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer orderId;
    private Integer itemId;
    private Integer quantity;
    private Integer customerId;
    private LocalDate orderDate;
    private String orderStatus;
}
