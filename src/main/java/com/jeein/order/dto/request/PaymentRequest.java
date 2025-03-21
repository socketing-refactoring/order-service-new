package com.jeein.order.dto.request;

import com.jeein.order.enums.TossPaymentMethod;
import com.jeein.order.enums.TossPaymentStatus;
import java.time.Instant;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PaymentRequest {
    private String tossPaymentKey;
    private String tossOrderId;
    private long totalAmount;
    private TossPaymentMethod tossPaymentMethod;
    private TossPaymentStatus tossPaymentStatus;
    private Instant requestedAt;
    private Instant approvedAt;
}
