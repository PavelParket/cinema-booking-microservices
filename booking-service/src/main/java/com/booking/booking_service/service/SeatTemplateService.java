package com.booking.booking_service.service;

import com.booking.booking_service.dto.SeatTemplateRequest;
import com.booking.booking_service.dto.SeatTemplateResponse;
import com.booking.booking_service.entity.SeatTemplate;
import com.booking.booking_service.mapper.SeatTemplateMapper;
import com.booking.booking_service.repository.SeatTemplateRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeatTemplateService {

    private final SeatTemplateRepository repository;

    private final SeatTemplateMapper mapper;

    @Transactional
    public SeatTemplateResponse create(SeatTemplateRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Transactional
    public SeatTemplateResponse update(Long id, SeatTemplateRequest request) {
        SeatTemplate seatTemplate = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SeatTemplate not found with id: " + id));

        mapper.updateEntityFromRequest(request, seatTemplate);

        return mapper.toResponse(repository.save(seatTemplate));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("SeatTemplate not found with id: " + id);
        }

        repository.deleteById(id);
    }

    public List<SeatTemplateResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public SeatTemplateResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("SeatTemplate not found with id: " + id));
    }

    public List<SeatTemplateResponse> getByScheme(Integer scheme) {
        return repository.findByScheme(scheme).stream()
                .map(mapper::toResponse)
                .toList();
    }
}
