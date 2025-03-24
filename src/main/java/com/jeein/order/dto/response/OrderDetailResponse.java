package com.jeein.order.dto.response;

import com.jeein.order.entity.Orders;
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
    private Instant createdAt;
    private Instant canceledAt;
    private String eventDatetimeId;
    private Instant eventDatetime;

    private OrderEvent orderEvent;
    private MemberResponse orderMember;

    private List<PaymentResponse> payments;
    private List<ReservationDetailResponse> reservationDetailList;

    public static OrderDetailResponse of(
            Orders order,
            String eventDatetimeId, // or UUID
            Instant eventDatetime,
            OrderEvent orderEvent,
            MemberResponse orderMember,
            List<ReservationDetailResponse> reservationDetailList) {
        return OrderDetailResponse.builder()
                .id(order.getId().toString())
                .canceledAt(order.getCanceledAt())
                .createdAt(order.getCreatedAt())
                .eventDatetimeId(eventDatetimeId)
                .eventDatetime(eventDatetime)
                .orderEvent(orderEvent)
                .orderMember(orderMember)
                .payments(order.getPayments().stream().map(PaymentResponse::fromEntity).toList())
                .reservationDetailList(reservationDetailList)
                .build();
    }
}
