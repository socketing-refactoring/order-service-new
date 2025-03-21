package com.jeein.order.feign;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EventResponse {
    private String id;
    private String title;
    private String description;
    private String place;
    private String artist;
    private List<EventDatetimeResponse> eventDatetimes;
    private Instant eventOpenTime;
    private Instant ticketingOpenTime;

    private String totalMap;
    private List<AreaResponse> areas;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class EventDatetimeResponse {
        private String id;
        private Instant datetime;
    }
}
