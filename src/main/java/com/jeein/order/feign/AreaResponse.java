package com.jeein.order.feign;

import java.util.List;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
// @JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AreaResponse {
    private String id;
    private String eventId;
    private String label;
    private int price;
    private String areaMap;

    private List<SeatResponse> seats;
}
