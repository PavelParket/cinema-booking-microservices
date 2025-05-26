package com.booking.movie_service.service;

import com.booking.movie_service.dto.GenreRequest;
import com.booking.movie_service.dto.GenreResponse;
import com.booking.movie_service.entity.Genre;
import com.booking.movie_service.mapper.GenreMapper;
import com.booking.movie_service.repository.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository repository;

    private final GenreMapper mapper;

    @Transactional
    public GenreResponse create(GenreRequest request) {
        Genre genre = mapper.toEntity(request);
        Genre savedGenre = repository.save(genre);
        return mapper.toResponse(savedGenre);
    }

    @Transactional
    public GenreResponse update(Long id, GenreRequest request) {
        Genre genre = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre not found with id: " + id));

        mapper.updateEntityFromRequest(request, genre);

        return mapper.toResponse(repository.save(genre));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Genre not found with id: " + id);
        }

        repository.deleteById(id);
    }

    public List<GenreResponse> getAll() {
        return mapper.toResponseList(repository.findAll());
    }

    public GenreResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Genre not found with id: " + id));
    }
}