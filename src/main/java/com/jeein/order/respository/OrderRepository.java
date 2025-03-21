package com.jeein.order.respository;

import com.jeein.order.entity.Orders;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID> {

    @Query("SELECT o FROM Orders o WHERE memberId = :memberId AND o.deletedAt IS NULL")
    List<Orders> findAllByMemberId(UUID memberId);

    @Query("SELECT o FROM Orders o WHERE o.id = :orderId AND o.deletedAt IS NULL")
    Optional<Orders> findById(UUID orderId);

    @Query(
            "SELECT o FROM Orders o WHERE o.eventDatetimeId = :eventDatetimeId AND o.canceledAt IS NULL AND o.deletedAt IS NULL")
    List<Orders> findByEventDatetimeIdWHERENOTCANCELED(UUID eventDatetimeId);

    @Query(
            "SELECT o FROM Orders o JOIN o.reservations r WHERE o.eventDatetimeId = :eventDatetimeId AND r.seatId = :seatId")
    List<Orders> findByEventDatetimeIdAndSeatId(UUID eventDatetimeId, UUID seatId);

    @Modifying
    @Query("UPDATE Orders o SET o.deletedAt = :deletedAt WHERE o.id = :orderId")
    void softDeleteOrder(UUID orderId, Instant deletedAt);

    @Modifying
    @Query("UPDATE Orders o SET o.canceledAt = :canceledAt WHERE o.id = :orderId")
    void cancelOrder(UUID orderId, Instant canceledAt);
}
