package com.jeein.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Reservation extends BaseEntity {
    @Column private UUID seatId;

    @ToString.Exclude @ManyToOne private Orders order;
}
