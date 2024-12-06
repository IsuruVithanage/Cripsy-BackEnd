package org.cripsy.OrderService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse {
    private String merchant_id;
    private String hash;

    public PaymentResponse(String merchant_id, String hash) {
        this.merchant_id = merchant_id;
        this.hash = hash;
    }
}
