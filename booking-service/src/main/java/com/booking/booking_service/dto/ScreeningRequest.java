package com.booking.booking_service.dto;

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
public class ScreeningRequest {

    private Long movieId;

    private LocalDateTime startTime;

    private Integer duration;

    private Integer hall;

    private BigDecimal basePrice;

    private Integer scheme;
}
