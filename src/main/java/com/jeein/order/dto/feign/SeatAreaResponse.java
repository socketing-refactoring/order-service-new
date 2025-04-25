package com.jeein.order.dto.feign;

import java.awt.geom.Area;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SeatAreaResponse {
    private String id;
    private int cx;
    private int cy;
    private int row;
    private int number;

    private Area area;

    @Getter
    public static class Area {
        private String id;
        private String label;
        private int price;

        private String eventId;
    }
}
