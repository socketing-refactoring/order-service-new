package com.jeein.order.entity;

import com.jeein.order.feign.MemberResponse;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class Orders extends DeletableEntity {
    @Column(nullable = false)
    private UUID memberId;

    @Column(nullable = false)
    private String memberName;

    @Column(nullable = false)
    private String memberEmail;

    @Column() private Instant canceledAt;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "order",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Payment> payments;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "order",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Reservation> reservations;

    public static Orders toEntity(MemberResponse member) {
        return Orders.builder()
                .memberId(UUID.fromString(member.getId()))
                .memberName(member.getName())
                .memberEmail(member.getEmail())
                .build();
    }

    public void addReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
