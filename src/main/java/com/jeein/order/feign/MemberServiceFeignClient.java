package com.jeein.order.feign;

import com.jeein.order.dto.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-service")
public interface MemberServiceFeignClient {
    @GetMapping("/api/v1/members/{id}")
    ResponseEntity<CommonResponse<MemberResponse>> getMember(@PathVariable("id") String memberId);
}
