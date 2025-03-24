package com.jeein.order.dto.response;

import com.jeein.order.entity.Reservation;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReservationResponse {
    private String id;
    private String seatId;

    public static ReservationResponse fromEntity(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId().toString())
                .seatId(reservation.getSeatId().toString())
                .build();
    }
}
