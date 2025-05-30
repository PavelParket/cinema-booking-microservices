package com.booking.booking_service.entity;

import com.booking.booking_service.util.SeatType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "seat_templates")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String seatNumber;

    @Column(nullable = false)
    private Integer row;

    @Column(nullable = false)
    private boolean available;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatType seatType;

    @Column(nullable = false)
    private BigDecimal priceMultiplier;

    @Column(nullable = false)
    private Integer scheme;
}
