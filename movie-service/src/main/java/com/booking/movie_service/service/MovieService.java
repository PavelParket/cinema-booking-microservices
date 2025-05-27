package com.booking.movie_service.service;

import com.booking.movie_service.dto.MovieRequest;
import com.booking.movie_service.dto.MovieResponse;
import com.booking.movie_service.entity.Movie;
import com.booking.movie_service.mapper.MovieMapper;
import com.booking.movie_service.repository.MovieRepository;
import com.booking.movie_service.resolver.GenreResolver;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    private final MovieMapper mapper;

    private final GenreResolver resolver;

    @Transactional
    public MovieResponse create(MovieRequest request) {
        Movie movie = mapper.toEntity(request, resolver);
        Movie savedMovie = repository.save(movie);

        return mapper.toResponse(savedMovie);
    }

    @Transactional
    public MovieResponse update(Long id, MovieRequest request) {
        Movie movie = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + id));

        mapper.updateEntityFromRequest(request, movie, resolver);

        return mapper.toResponse(repository.save(movie));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Movie not found with id: " + id);
        }

        repository.deleteById(id);
    }

    public List<MovieResponse> getAll() {
        return mapper.toResponseList(repository.findAll());
    }

    public MovieResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + id));
    }

    public List<MovieResponse> findByTitle(String title) {
        return mapper.toResponseList(repository.findByTitle(title));
    }

    public List<MovieResponse> findByDirector(String director) {
        return mapper.toResponseList(repository.findByDirector(director));
    }

    public List<MovieResponse> findByCountry(String country) {
        return mapper.toResponseList(repository.findByCountry(country));
    }

    public List<MovieResponse> findByRelease(LocalDate date) {
        return mapper.toResponseList(repository.findByRelease(date));
    }

    public List<MovieResponse> findByReleaseBetween(LocalDate from, LocalDate to) {
        return mapper.toResponseList(repository.findByReleaseBetween(from, to));
    }

    public List<MovieResponse> findByGenreIds(List<Long> genreIds) {
        return mapper.toResponseList(repository.findByGenresIdIn(genreIds));
    }
}
