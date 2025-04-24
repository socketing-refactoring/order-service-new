package com.jeein.order.feign;

import com.jeein.order.dto.feign.TossPaymentRequest;
import com.jeein.order.dto.feign.TossPaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "toss-payment", url = "https://api.tosspayments.com")
public interface TossPaymentFeignClient {

    @PostMapping(value = "/v1/payments/confirm", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TossPaymentResponse> createPayment(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @RequestBody TossPaymentRequest tossPaymentRequest);
}
