package com.booking.booking_service.dto;

import com.booking.booking_service.util.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatResponse {

    private Long id;

    private String seatNumber;

    private Integer row;

    private boolean available;

    private SeatType seatType;

    private BigDecimal priceMultiplier;

    private Long screeningId;
}
