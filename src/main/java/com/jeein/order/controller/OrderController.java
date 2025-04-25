package com.jeein.order.controller;

import com.jeein.order.dto.CommonResponse;
import com.jeein.order.dto.request.OrderRequest;
import com.jeein.order.dto.response.FlatReservationResponse;
import com.jeein.order.dto.response.OrderDetailResponse;
import com.jeein.order.exception.CustomValidationException;
import com.jeein.order.exception.ErrorCode;
import com.jeein.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    /* 주문 목록 상세 조회 (공연, 예매자 정보 포함) */
    @GetMapping("/detail")
    public ResponseEntity<CommonResponse<List<OrderDetailResponse>>> getDetailedOrderList(
            @RequestParam String memberId) {
        return ResponseEntity.ok(orderService.getOrderDetailList(memberId));
    }

    /* 예약 목록 조회 (공연 정보 미포함, 예매자 정보 포함) */
    @GetMapping("/reservers")
    public ResponseEntity<CommonResponse<List<FlatReservationResponse>>>
            getReservationDetailListByEvent(
                    @RequestParam(required = false) String eventDatetimeId) {
        return ResponseEntity.ok(orderService.getReservationDetailListByEvent(eventDatetimeId));
    }

    /* 단일 주문 상세 조회 */
    @GetMapping("/{orderId}")
    public ResponseEntity<CommonResponse<OrderDetailResponse>> getOreOrderDetail(
            @PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOneOrderDetail(orderId));
    }

    /* 주문 취소 */
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<CommonResponse<Object>> cancelOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }

    /* 단일 주문 소프트 삭제 (reservation 테이블도 소프트 삭제) */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<CommonResponse<Object>> softDeleteOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.softDeleteOrder(orderId));
    }

    /* 주문 생성 */
    @PostMapping
    public ResponseEntity<CommonResponse<OrderDetailResponse>> createOrder(
            @Valid @RequestBody OrderRequest orderRequest, HttpServletRequest request) {
        // x-api-로 시작하는 모든 헤더 로깅
        request.getHeaderNames()
                .asIterator()
                .forEachRemaining(
                        headerName -> {
                            if (headerName.toLowerCase().startsWith("x-api-")) {
                                log.info(
                                        "Custom Header: {} = {}",
                                        headerName,
                                        request.getHeader(headerName));
                            }
                        });

        // Gateway Server의 JWTAuthenticationFilter에서 헤더에 추가한 회원 정보 추출
        String memberId =
                Optional.ofNullable(request.getHeader("x-api-userid"))
                        .filter(header -> !header.isEmpty())
                        .orElseThrow(() -> new CustomValidationException(ErrorCode.INVALID_TOKEN));

        return ResponseEntity.ok(orderService.createOrder(orderRequest, memberId));
    }
}
