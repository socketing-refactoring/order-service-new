package com.jeein.order.controller;

import com.jeein.order.dto.CommonResponse;
import com.jeein.order.dto.request.OrderRequest;
import com.jeein.order.dto.response.FlatReservationResponse;
import com.jeein.order.dto.response.OrderDetailResponse;
import com.jeein.order.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
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
            getReservationDetailListByEvent(@RequestParam String eventDatetimeId) {
        return ResponseEntity.ok(orderService.getReservationDetailListByEvent(eventDatetimeId));
    }

    /* 단일 주문 상세 조회 */
    @GetMapping("/{orderId}")
    public ResponseEntity<CommonResponse<OrderDetailResponse>> getOreOrderDetail(
            @PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOneOrderDetail(orderId));
    }

    @PostMapping("/{orderId}/status")
    public ResponseEntity<CommonResponse<Object>> cancelOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<CommonResponse<Object>> softDeleteOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.softDeleteOrder(orderId));
    }

    @PostMapping
    public ResponseEntity<CommonResponse<OrderDetailResponse>> createOrder(
            @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }
}
