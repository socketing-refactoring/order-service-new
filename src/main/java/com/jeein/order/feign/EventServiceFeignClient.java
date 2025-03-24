package com.jeein.order.feign;

import com.jeein.order.dto.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "event-service")
public interface EventServiceFeignClient {

    @GetMapping("/api/v1/events")
    public ResponseEntity<CommonResponse<EventResponse>> getOneEventDetailsByEventDatetimeId(
            @RequestParam String eventDatetimeId);
}
