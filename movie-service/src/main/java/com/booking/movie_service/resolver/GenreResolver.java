package com.booking.movie_service.resolver;

import com.booking.movie_service.entity.Genre;
import com.booking.movie_service.repository.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreResolver {

    private final GenreRepository repository;

    public List<Genre> resolveGenresByIds(List<Long> ids) {
        List<Genre> genres = repository.findAllById(ids);
        if (genres.size() != ids.size()) {
            throw new EntityNotFoundException("Some genres not found for IDs: " + ids);
        }
        return genres;
    }
}
