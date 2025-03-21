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

    @GetMapping
    public ResponseEntity<CommonResponse<List<OrderDetailResponse>>> getOrderList(
            @RequestParam String memberId) {
        return ResponseEntity.ok(orderService.getOrderDetailList(memberId));
    }

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

    @GetMapping("/reservations")
    public ResponseEntity<CommonResponse<List<FlatReservationResponse>>>
            getReservationDetailListByEvent(
                    @RequestParam String eventId, @RequestParam String eventDatetimeId) {
        return ResponseEntity.ok(
                orderService.getReservationDetailListByEvent(eventId, eventDatetimeId));
    }

    @PostMapping
    public ResponseEntity<CommonResponse<OrderDetailResponse>> createOrder(
            @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }
}
