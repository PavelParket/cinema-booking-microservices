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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Transactional(readOnly = true)
    public List<GenreResponse> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(genreMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GenreResponse getGenreById(Long id) {
        return genreRepository.findById(id)
                .map(genreMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Genre not found with id: " + id));
    }

    @Transactional
    public GenreResponse createGenre(GenreRequest request) {
        Genre genre = genreMapper.toEntity(request);
        Genre savedGenre = genreRepository.save(genre);
        return genreMapper.toResponse(savedGenre);
    }

    @Transactional
    public GenreResponse updateGenre(Long id, GenreRequest request) {
        Genre existingGenre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre not found with id: " + id));

        Genre genreToUpdate = genreMapper.toEntity(request);
        genreToUpdate.setId(existingGenre.getId());
        
        Genre updatedGenre = genreRepository.save(genreToUpdate);
        return genreMapper.toResponse(updatedGenre);
    }

    @Transactional
    public void deleteGenre(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new EntityNotFoundException("Genre not found with id: " + id);
        }
        genreRepository.deleteById(id);
    }
} 