package com.jeein.order.entity;

import com.jeein.order.dto.request.PaymentRequest;
import com.jeein.order.enums.TossPaymentMethod;
import com.jeein.order.enums.TossPaymentStatus;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class Payment extends BaseEntity {
    @ManyToOne @ToString.Exclude private Orders order;

    @Column(nullable = false, unique = true)
    String tossPaymentKey;

    @Column(nullable = false)
    String tossOrderId;

    @Column(nullable = false)
    long totalAmount;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    TossPaymentMethod tossPaymentMethod;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    TossPaymentStatus tossPaymentStatus;

    @Column(nullable = false)
    Instant requestedAt;

    Instant approvedAt;

    public static Payment toEntity(PaymentRequest paymentRequest, Orders order) {
        return Payment.builder()
                .tossPaymentKey(paymentRequest.getTossPaymentKey())
                .tossOrderId(paymentRequest.getTossOrderId())
                .totalAmount(paymentRequest.getTotalAmount())
                .tossPaymentMethod(paymentRequest.getTossPaymentMethod())
                .tossPaymentStatus(paymentRequest.getTossPaymentStatus())
                .requestedAt(paymentRequest.getRequestedAt())
                .approvedAt(paymentRequest.getApprovedAt())
                .build();
    }
}
