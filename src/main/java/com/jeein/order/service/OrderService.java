package com.jeein.order.service;

import com.jeein.order.dto.CommonResponse;
import com.jeein.order.dto.request.OrderRequest;
import com.jeein.order.dto.response.FlatReservationResponse;
import com.jeein.order.dto.response.OrderDetailResponse;
import com.jeein.order.dto.response.OrderEvent;
import com.jeein.order.dto.response.ReservationDetail;
import com.jeein.order.entity.Orders;
import com.jeein.order.entity.Payment;
import com.jeein.order.entity.Reservation;
import com.jeein.order.exception.*;
import com.jeein.order.feign.EventResponse;
import com.jeein.order.feign.EventServiceFeignClient;
import com.jeein.order.feign.MemberResponse;
import com.jeein.order.feign.MemberServiceFeignClient;
import com.jeein.order.respository.OrderRepository;
import java.time.Instant;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberServiceFeignClient memberServiceFeignClient;
    private final EventServiceFeignClient eventServiceFeignClient;

    @Transactional(readOnly = true)
    public CommonResponse<List<OrderDetailResponse>> getOrderDetailList(String memberId) {
        // 회원 조회
        ResponseEntity<CommonResponse<MemberResponse>> memberResponse =
                memberServiceFeignClient.getMember(memberId);
        if (memberResponse.getStatusCode().isError()) {
            throw new FeignServiceException(ErrorCode.MEMBER_FEIGN_ERROR);
        }

        MemberResponse member = Objects.requireNonNull(memberResponse.getBody()).getData();
        if (member == null) {
            log.error(memberResponse.getBody().getMessage());
            throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);
        }

        List<Orders> orders = orderRepository.findAllByMemberId(UUID.fromString(memberId));

        // 주문 확인용 응답 객체 생성
        List<OrderDetailResponse> response = new ArrayList<>();
        for (Orders order : orders) {
            ResponseEntity<CommonResponse<EventResponse>> eventResponse =
                    eventServiceFeignClient.getOneEventDetails(order.getEventId().toString());
            if (eventResponse.getStatusCode().isError()) {
                throw new FeignServiceException(ErrorCode.EVENT_FEIGN_ERROR);
            }

            EventResponse event = Objects.requireNonNull(eventResponse.getBody()).getData();
            if (event == null) {
                log.error(eventResponse.getBody().getMessage());
                throw new EventException(ErrorCode.EVENT_NOT_FOUND);
            }

            OrderEvent orderEvent = OrderEvent.convertEventResponseToOrderEvent(event);
            Optional<Instant> eventDatetime =
                    Optional.ofNullable(
                            event.getEventDatetimes().stream()
                                    .filter(
                                            datetime ->
                                                    datetime.getId()
                                                            .equals(
                                                                    order.getEventDatetimeId()
                                                                            .toString()))
                                    .map(EventResponse.EventDatetimeResponse::getDatetime)
                                    .findFirst()
                                    .orElseThrow(
                                            () ->
                                                    new EventException(
                                                            ErrorCode.EVENT_DATETIME_NOT_FOUND)));
            ;

            List<ReservationDetail> reservationDetailList = new ArrayList<>();
            for (Reservation reservation : order.getReservations()) {
                event.getAreas().stream()
                        .flatMap(
                                area ->
                                        area.getSeats().stream()
                                                .filter(
                                                        seat ->
                                                                reservation
                                                                        .getSeatId()
                                                                        .toString()
                                                                        .contains(seat.getId()))
                                                .map(
                                                        seat ->
                                                                ReservationDetail.builder()
                                                                        .id(
                                                                                reservation
                                                                                        .getId()
                                                                                        .toString())
                                                                        .seatId(seat.getId())
                                                                        .seatRow(seat.getRow())
                                                                        .seatNumber(
                                                                                seat.getNumber())
                                                                        .areaId(area.getId())
                                                                        .areaLabel(area.getLabel())
                                                                        .areaPrice(area.getPrice())
                                                                        .build()))
                        .forEach(reservationDetailList::add);
            }
            OrderDetailResponse orderDetail =
                    OrderDetailResponse.fromEntity(
                            order, eventDatetime.get(), orderEvent, member, reservationDetailList);
            response.add(orderDetail);
        }

        return CommonResponse.success("전체 주문 조회 성공", "0", response);
    }

    @Transactional(readOnly = true)
    public CommonResponse<OrderDetailResponse> getOneOrderDetail(String orderId) {
        Orders order =
                orderRepository
                        .findById(UUID.fromString(orderId))
                        .orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));

        // 회원 조회
        ResponseEntity<CommonResponse<MemberResponse>> memberResponse =
                memberServiceFeignClient.getMember(order.getMemberId().toString());
        if (memberResponse.getStatusCode().isError()) {
            throw new FeignServiceException(ErrorCode.MEMBER_FEIGN_ERROR);
        }

        MemberResponse member = Objects.requireNonNull(memberResponse.getBody()).getData();
        if (member == null) {
            log.error(memberResponse.getBody().getMessage());
            throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);
        }
        // 주문 확인용 응답 객체 생성
        log.info("event id: {}", order.getEventId().toString());
        ResponseEntity<CommonResponse<EventResponse>> eventResponse =
                eventServiceFeignClient.getOneEventDetails(order.getEventId().toString());
        if (eventResponse.getStatusCode().isError()) {
            throw new FeignServiceException(ErrorCode.EVENT_FEIGN_ERROR);
        }

        EventResponse event = Objects.requireNonNull(eventResponse.getBody()).getData();
        if (event == null) {
            log.error(eventResponse.getBody().getMessage());
            throw new EventException(ErrorCode.EVENT_NOT_FOUND);
        }

        OrderEvent orderEvent = OrderEvent.convertEventResponseToOrderEvent(event);
        Optional<Instant> eventDatetime =
                Optional.ofNullable(
                        event.getEventDatetimes().stream()
                                .filter(
                                        datetime ->
                                                datetime.getId()
                                                        .equals(
                                                                order.getEventDatetimeId()
                                                                        .toString()))
                                .map(EventResponse.EventDatetimeResponse::getDatetime)
                                .findFirst()
                                .orElseThrow(
                                        () ->
                                                new EventException(
                                                        ErrorCode.EVENT_DATETIME_NOT_FOUND)));
        ;

        List<ReservationDetail> reservationDetailList = new ArrayList<>();
        for (Reservation reservation : order.getReservations()) {
            event.getAreas().stream()
                    .flatMap(
                            area ->
                                    area.getSeats().stream()
                                            .filter(
                                                    seat ->
                                                            reservation
                                                                    .getSeatId()
                                                                    .toString()
                                                                    .contains(seat.getId()))
                                            .map(
                                                    seat ->
                                                            ReservationDetail.builder()
                                                                    .id(
                                                                            reservation
                                                                                    .getId()
                                                                                    .toString())
                                                                    .seatId(seat.getId())
                                                                    .seatRow(seat.getRow())
                                                                    .seatNumber(seat.getNumber())
                                                                    .areaId(area.getId())
                                                                    .areaLabel(area.getLabel())
                                                                    .areaPrice(area.getPrice())
                                                                    .build()))
                    .forEach(reservationDetailList::add);
        }
        OrderDetailResponse orderDetail =
                OrderDetailResponse.fromEntity(
                        order, eventDatetime.get(), orderEvent, member, reservationDetailList);

        return CommonResponse.success("전체 주문 조회 성공", "0", orderDetail);
    }

    @Transactional(readOnly = true)
    public CommonResponse<List<FlatReservationResponse>> getReservationDetailListByEvent(
            String eventId, String eventDatetimeId) {
        List<Orders> orderList =
                orderRepository.findByEventDatetimeIdWHERENOTCANCELED(
                        UUID.fromString(eventDatetimeId));

        List<FlatReservationResponse> flatReservationResponseList = new ArrayList<>();
        for (Orders order : orderList) {
            List<Reservation> reservations = order.getReservations();
            for (Reservation reservation : reservations) {
                FlatReservationResponse flatReservationResponse =
                        FlatReservationResponse.builder()
                                .id(reservation.getId().toString())
                                .seatId(reservation.getSeatId().toString())
                                .reserverId(order.getMemberId().toString())
                                .reserverName(order.getMemberName())
                                .reserverEmail(order.getMemberEmail())
                                .build();
                flatReservationResponseList.add(flatReservationResponse);
            }
        }

        return CommonResponse.success("공연의 좌석 예약 현황 조회 성공", "0", flatReservationResponseList);
    }

    @Transactional
    public CommonResponse<OrderDetailResponse> createOrder(OrderRequest orderRequest) {
        // 좌석 유효성 검사 추가
        List<Orders> existingOrderList = new ArrayList<>();
        for (String seatId : orderRequest.getSeatIds()) {
            List<Orders> existingOrder =
                    orderRepository.findByEventDatetimeIdAndSeatId(
                            UUID.fromString(orderRequest.getEventDatetimeId()),
                            UUID.fromString(seatId));
            existingOrderList.addAll(existingOrder);
        }
        if (existingOrderList.size() > 0) {
            throw new OrderException(ErrorCode.ORDER_ALREADY_EXISTS);
        }

        // 회원 유효성 검사
        log.info(orderRequest.getMemberId());
        ResponseEntity<CommonResponse<MemberResponse>> memberResponse =
                memberServiceFeignClient.getMember(orderRequest.getMemberId());
        if (memberResponse.getStatusCode().isError()) {
            throw new FeignServiceException(ErrorCode.MEMBER_FEIGN_ERROR);
        }

        MemberResponse member = Objects.requireNonNull(memberResponse.getBody()).getData();
        if (member == null) {
            log.error(memberResponse.getBody().getMessage());
            throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);
        }

        // order, reservation, payment 저장
        Orders order = Orders.toEntity(orderRequest, member);
        order.addReservations(
                orderRequest.getSeatIds().stream()
                        .map(seatId -> new Reservation(UUID.fromString(seatId), order))
                        .toList());
        order.addPayments(
                orderRequest.getPayments().stream()
                        .map(paymentRequest -> Payment.toEntity(paymentRequest, order))
                        .toList());
        Orders savedOrder = orderRepository.save(order);

        // 주문 확인용 응답 객체 생성
        ResponseEntity<CommonResponse<EventResponse>> eventResponse =
                eventServiceFeignClient.getOneEventDetails(orderRequest.getEventId());
        if (eventResponse.getStatusCode().isError()) {
            throw new FeignServiceException(ErrorCode.EVENT_FEIGN_ERROR);
        }

        EventResponse event = Objects.requireNonNull(eventResponse.getBody()).getData();
        if (event == null) {
            log.error(eventResponse.getBody().getMessage());
            throw new EventException(ErrorCode.EVENT_NOT_FOUND);
        }

        OrderEvent orderEvent = OrderEvent.convertEventResponseToOrderEvent(event);
        Optional<Instant> eventDatetime =
                Optional.ofNullable(
                        event.getEventDatetimes().stream()
                                .filter(
                                        datetime ->
                                                datetime.getId()
                                                        .equals(
                                                                savedOrder
                                                                        .getEventDatetimeId()
                                                                        .toString()))
                                .map(EventResponse.EventDatetimeResponse::getDatetime)
                                .findFirst()
                                .orElseThrow(
                                        () ->
                                                new EventException(
                                                        ErrorCode.EVENT_DATETIME_NOT_FOUND)));
        ;

        List<ReservationDetail> reservationDetailList = new ArrayList<>();
        for (Reservation reservation : savedOrder.getReservations()) {
            event.getAreas().stream()
                    .flatMap(
                            area ->
                                    area.getSeats().stream()
                                            .filter(
                                                    seat ->
                                                            reservation
                                                                    .getSeatId()
                                                                    .toString()
                                                                    .contains(seat.getId()))
                                            .map(
                                                    seat ->
                                                            ReservationDetail.builder()
                                                                    .id(
                                                                            reservation
                                                                                    .getId()
                                                                                    .toString())
                                                                    .seatId(seat.getId())
                                                                    .seatRow(seat.getRow())
                                                                    .seatNumber(seat.getNumber())
                                                                    .areaId(area.getId())
                                                                    .areaLabel(area.getLabel())
                                                                    .areaPrice(area.getPrice())
                                                                    .build()))
                    .forEach(reservationDetailList::add);
        }

        OrderDetailResponse response =
                OrderDetailResponse.fromEntity(
                        savedOrder, eventDatetime.get(), orderEvent, member, reservationDetailList);
        return CommonResponse.success("주문 생성 성공", "0", response);
    }

    @Transactional
    public CommonResponse<Object> cancelOrder(String orderId) {
        Orders order =
                orderRepository
                        .findById(UUID.fromString(orderId))
                        .orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getCanceledAt() != null) {
            throw new OrderException(ErrorCode.ORDER_ALREADY_CANCELED);
        }

        // 페이먼트 취소 로직 추가

        orderRepository.cancelOrder(UUID.fromString(orderId), Instant.now());

        return CommonResponse.success("주문 취소 성공", "0", null);
    }

    @Transactional
    public CommonResponse<Object> softDeleteOrder(String orderId) {
        Orders order =
                orderRepository
                        .findById(UUID.fromString(orderId))
                        .orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));

        //        if (order.getDeletedAt() != null) {
        //            throw new OrderException(ErrorCode.ORDER_ALREADY_DELETED);
        //        }

        orderRepository.softDeleteOrder(UUID.fromString(orderId), Instant.now());

        return CommonResponse.success("주문 삭제 성공", "0", null);
    }
}
