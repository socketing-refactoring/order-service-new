package com.jeein.order.feign;

import com.jeein.order.dto.CommonResponse;
import com.jeein.order.dto.feign.EventResponse;
import com.jeein.order.dto.feign.SeatAreaResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "event-service")
public interface EventServiceFeignClient {

    @GetMapping("/api/v1/events/{eventId}")
    public ResponseEntity<CommonResponse<EventResponse>> getOneEvent(@PathVariable String eventId);

    @GetMapping("/api/v1/events/{eventId}/detail")
    public ResponseEntity<CommonResponse<EventResponse>> getOneEventDetail(
            @PathVariable String eventId);

    @GetMapping("/api/v2/events/{eventId}/seats")
    public ResponseEntity<CommonResponse<List<SeatAreaResponse>>> getEventSeats(
            @PathVariable String eventId,
            @RequestParam List<String> seatIds,
            @RequestParam boolean includeArea);
}
