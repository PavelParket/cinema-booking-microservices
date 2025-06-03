package com.booking.booking_service.service;

import com.booking.booking_service.dto.ScreeningRequest;
import com.booking.booking_service.dto.ScreeningResponse;
import com.booking.booking_service.entity.Screening;
import com.booking.booking_service.mapper.ScreeningMapper;
import com.booking.booking_service.mapper.SeatMapper;
import com.booking.booking_service.repository.ScreeningRepository;
import com.booking.booking_service.repository.SeatTemplateRepository;
import com.booking.booking_service.util.ScreeningStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository repository;

    private final ScreeningMapper mapper;

    private final SeatMapper seatMapper;

    private final SeatTemplateRepository seatTemplateRepository;

    @Transactional
    public ScreeningResponse create(ScreeningRequest request) {
        Screening screening = mapper.toEntity(request);
        screening.setStatus(ScreeningStatus.SCHEDULED);
        screening.setSeats(seatMapper.toEntityList(seatTemplateRepository.findByScheme(request.getScheme())));

        return mapper.toResponse(repository.save(screening));
    }

    @Transactional
    public ScreeningResponse update(Long id, ScreeningRequest request) {
        Screening screening = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Screening not found with id: " + id));

        mapper.updateEntityFromRequest(request, screening);

        return mapper.toResponse(repository.save(screening));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Screening not found with id: " + id);
        }

        repository.deleteById(id);
    }

    public List<ScreeningResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ScreeningResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Screening not found with id: " + id));
    }

    @Transactional
    public void updateStatus(Long id, String status) {
        Screening screening = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Screening not found with id: " + id));

        ScreeningStatus enumStatus;

        try {
            enumStatus = ScreeningStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }

        screening.setStatus(enumStatus);
        repository.save(screening);
    }
}
