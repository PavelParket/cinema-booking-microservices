package com.booking.booking_service.service;

import com.booking.booking_service.dto.BookingRequest;
import com.booking.booking_service.dto.BookingResponse;
import com.booking.booking_service.entity.Booking;
import com.booking.booking_service.entity.Screening;
import com.booking.booking_service.entity.Seat;
import com.booking.booking_service.mapper.BookingMapper;
import com.booking.booking_service.repository.BookingRepository;
import com.booking.booking_service.repository.ScreeningRepository;
import com.booking.booking_service.util.BookingStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository repository;

    private final BookingMapper mapper;

    private final ScreeningRepository screeningRepository;

    @Transactional
    public BookingResponse create(BookingRequest request) {
        Booking booking = mapper.toEntity(request);
        booking.setStatus(BookingStatus.ACTIVE);

        Screening screening = screeningRepository.findById(request.getScreeningId())
                .orElseThrow(() -> new EntityNotFoundException("Screening not found with id: " + request.getScreeningId()));
        booking.setScreening(screening);

        Seat seat = screening.getSeats().stream()
                .filter(s -> s.getSeatNumber().equals(request.getSeatNumber()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Seat not found with number: " + request.getSeatNumber()));

        booking.setPrice(screening.getBasePrice().multiply(seat.getPriceMultiplier()));

        return mapper.toResponse(repository.save(booking));
    }

    @Transactional
    public BookingResponse update(Long id, BookingRequest request) {
        Booking booking = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        mapper.updateEntityFromRequest(request, booking);

        Screening screening = screeningRepository.findById(request.getScreeningId())
                .orElseThrow(() -> new EntityNotFoundException("Screening not found with id: " + request.getScreeningId()));

        Seat seat = screening.getSeats().stream()
                .filter(s -> s.getSeatNumber().equals(request.getSeatNumber()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Seat not found with number: " + request.getSeatNumber()));

        booking.setPrice(screening.getBasePrice().multiply(seat.getPriceMultiplier()));

        return mapper.toResponse(repository.save(booking));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Booking not found with id: " + id);
        }

        repository.deleteById(id);
    }

    public List<BookingResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public BookingResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));
    }

    @Transactional
    public void updateStatus(Long id, String status) {
        Booking booking = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        BookingStatus enumStatus;

        try {
            enumStatus = BookingStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }

        booking.setStatus(enumStatus);
        repository.save(booking);
    }
}
