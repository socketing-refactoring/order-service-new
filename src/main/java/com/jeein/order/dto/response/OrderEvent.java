package com.jeein.order.dto.response;

import com.jeein.order.feign.EventResponse;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderEvent {
    private String id;
    private String title;
    private String description;
    private String place;
    private String artist;

    public static OrderEvent convertEventResponseToOrderEvent(EventResponse event) {
        return OrderEvent.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .place(event.getPlace())
                .artist(event.getArtist())
                .build();
    }
}
