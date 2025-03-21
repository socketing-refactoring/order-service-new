package com.jeein.order.dto.response;

import com.jeein.order.entity.Orders;
import com.jeein.order.entity.Payment;
import com.jeein.order.enums.TossPaymentMethod;
import com.jeein.order.enums.TossPaymentStatus;
import com.jeein.order.feign.MemberResponse;
import java.time.Instant;
import java.util.List;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    private String id;
    private String eventDatetimeId;
    private Instant eventDatetime;
    private Instant createdAt;
    private Instant canceledAt;

    private OrderEvent orderEvent;
    private MemberResponse orderMember;

    private List<PaymentResponse> payments;
    private List<ReservationDetail> reservationDetailList;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    private static class PaymentResponse {
        private String id;
        private long totalAmount;
        private TossPaymentMethod tossPaymentMethod;
        private TossPaymentStatus tossPaymentStatus;
        private Instant requestedAt;
        private Instant approvedAt;

        private static PaymentResponse fromEntity(Payment payment) {
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

    public static OrderDetailResponse fromEntity(
            Orders order,
            Instant eventDatetime,
            OrderEvent orderEvent,
            MemberResponse orderMember,
            List<ReservationDetail> reservationDetailList) {
        return OrderDetailResponse.builder()
                .id(order.getId().toString())
                .eventDatetimeId(order.getEventDatetimeId().toString())
                .eventDatetime(eventDatetime)
                .canceledAt(order.getCanceledAt())
                .createdAt(order.getCreatedAt())
                .orderEvent(orderEvent)
                .orderMember(orderMember)
                .payments(order.getPayments().stream().map(PaymentResponse::fromEntity).toList())
                .reservationDetailList(reservationDetailList)
                .build();
    }
}
