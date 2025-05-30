package com.booking.booking_service.dto;

import com.booking.booking_service.entity.Seat;
import com.booking.booking_service.util.ScreeningStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScreeningResponse {

    private Long id;

    private Long movieId;

    private LocalDateTime startTime;

    private Integer duration;

    private Integer hall;

    private BigDecimal basePrice;

    private ScreeningStatus status;

    private List<Long> seats;
}
