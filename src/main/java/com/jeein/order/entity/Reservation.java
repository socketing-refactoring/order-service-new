package com.jeein.order.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
@Table(
        uniqueConstraints =
                @UniqueConstraint(columnNames = {"event_datetime_id", "seat_id", "deleted_at"}))
public class Reservation extends DeletableEntity {
    @Column private UUID seatId;

    @Column(nullable = false)
    private UUID eventDatetimeId;

    @ToString.Exclude @ManyToOne private Orders order;
}
