package com.jeein.order.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReservationDetailResponse {
    private String id;
    private String seatId;
    private int seatRow;
    private int seatNumber;
    private String areaId;
    private String areaLabel;
    private int areaPrice;
}
