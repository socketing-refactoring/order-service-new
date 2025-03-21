package com.jeein.order.feign;

import com.jeein.order.dto.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "event-service")
public interface EventServiceFeignClient {

    @GetMapping("/api/v1/events/{eventId}/details")
    public ResponseEntity<CommonResponse<EventResponse>> getOneEventDetails(
            @PathVariable String eventId);
}
