package com.jeein.order.dto.feign;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TossPaymentResponse {
    private String paymentKey;
    private String orderId;
    private String method;
    private String status;
    private int amount;
    private String currency;
    private String lastTransactionKey;
    private String requestedAt;
    private String approvedAt;
    private String failReason;
    private String failCode;
}
