package com.jeein.order.dto.response;

import com.jeein.order.entity.Payment;
import java.time.Instant;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private String id;
    private long totalAmount;
    private String tossPaymentMethod;
    private String tossPaymentStatus;
    private Instant requestedAt;
    private Instant approvedAt;

    public static PaymentResponse fromEntity(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId().toString())
                .totalAmount(payment.getTotalAmount())
                .tossPaymentMethod(payment.getTossPaymentMethod())
                .tossPaymentStatus(payment.getTossPaymentStatus())
                .requestedAt(payment.getRequestedAt())
                .approvedAt(payment.getApprovedAt())
                .build();
    }
}
