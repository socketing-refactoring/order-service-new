package com.jeein.order.dto.feign;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TossPaymentRequest {
    private String orderId;
    private int amount;
    private String paymentKey;
}
