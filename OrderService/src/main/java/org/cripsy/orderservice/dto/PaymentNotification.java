package org.cripsy.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentNotification {
    private String order_id;
    private String payhere_amount;
    private String payhere_currency;
    private String status_code;
    private String md5sig;
}
