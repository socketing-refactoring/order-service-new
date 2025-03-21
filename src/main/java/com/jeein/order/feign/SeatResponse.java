package com.jeein.order.feign;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SeatResponse {
    private String id;
    private String areaId;
    private int cx;
    private int cy;
    private int row;
    private int number;
}
