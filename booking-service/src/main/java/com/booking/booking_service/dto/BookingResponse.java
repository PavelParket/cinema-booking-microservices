package com.booking.booking_service.dto;

import com.booking.booking_service.util.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {

    private Long id;

    private Long userId;

    private Long screeningId;

    private Integer seatNumber;

    private BookingStatus status;

    private LocalDateTime createdAt;

    private BigDecimal price;
}
