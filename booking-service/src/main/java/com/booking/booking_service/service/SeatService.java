package com.booking.booking_service.service;

import com.booking.booking_service.dto.SeatRequest;
import com.booking.booking_service.dto.SeatResponse;
import com.booking.booking_service.entity.Seat;
import com.booking.booking_service.mapper.SeatMapper;
import com.booking.booking_service.repository.SeatRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository repository;

    private final SeatMapper mapper;

    @Transactional
    public SeatResponse create(SeatRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Transactional
    public SeatResponse update(Long id, SeatRequest request) {
        Seat seat = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seat not found with id: " + id));

        mapper.updateEntityFromRequest(request, seat);

        return mapper.toResponse(repository.save(seat));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Seat not found with id: " + id);
        }

        repository.deleteById(id);
    }

    public List<SeatResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public SeatResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Seat not found with id: " + id));
    }
}
