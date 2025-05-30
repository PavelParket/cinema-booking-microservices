package com.booking.booking_service.service;

import com.booking.booking_service.mapper.ScreeningMapper;
import com.booking.booking_service.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;

    private final ScreeningMapper screeningMapper;
}
