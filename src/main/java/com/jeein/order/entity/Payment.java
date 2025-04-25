package com.jeein.order.entity;

import com.jeein.order.dto.feign.TossPaymentResponse;
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
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(nullable = false)
    private Orders order;

    @Column(nullable = false)
    long totalAmount;

    @Column String tossOrderId;

    @Column String tossPaymentKey;

    @Column String tossPaymentMethod;

    @Column String tossPaymentStatus;

    @Column(nullable = false)
    Instant requestedAt;

    @Column Instant approvedAt;

    public static Payment toEntity(TossPaymentResponse paymentResponse, Orders order) {
        return Payment.builder()
                .order(order)
                .tossPaymentKey(paymentResponse.getPaymentKey())
                .tossOrderId(paymentResponse.getOrderId())
                .totalAmount(paymentResponse.getAmount())
                .tossPaymentMethod(paymentResponse.getMethod())
                .tossPaymentStatus(paymentResponse.getStatus())
                .requestedAt(Instant.parse(paymentResponse.getRequestedAt()))
                .approvedAt(Instant.parse(paymentResponse.getApprovedAt()))
                .build();
    }
}
