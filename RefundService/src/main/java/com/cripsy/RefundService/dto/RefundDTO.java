package org.cripsy.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundDTO {
    private Integer refundId;
    private Integer orderId;
    private LocalDate refundRequestDate;
    private String refundState;
}
