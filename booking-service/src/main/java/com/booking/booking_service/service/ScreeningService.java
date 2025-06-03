package com.booking.booking_service.service;

import com.booking.booking_service.entity.Screening;
import com.booking.booking_service.entity.Seat;
import com.booking.booking_service.entity.SeatTemplate;
import com.booking.booking_service.mapper.ScreeningMapper;
import com.booking.booking_service.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;

    private final ScreeningMapper screeningMapper;

    public void method(List<SeatTemplate> seatTemplateList) {
        Screening screening = new Screening();


    }
}
