package com.jeein.order.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FlatReservationResponse {
    private String id;
    private String seatId;
    private String reserverId;
    private String reserverName;
    private String reserverEmail;
}
