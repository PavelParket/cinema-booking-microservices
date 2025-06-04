package com.booking.movie_service.controller;

import com.booking.movie_service.dto.MovieRequest;
import com.booking.movie_service.dto.MovieResponse;
import com.booking.movie_service.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @PostMapping
    public ResponseEntity<MovieResponse> create(@RequestBody MovieRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> update(@PathVariable Long id, @RequestBody MovieRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<MovieResponse>> findByTitle(@RequestParam String title) {
        return ResponseEntity.ok(service.findByTitle(title));
    }

    @GetMapping("/search/director")
    public ResponseEntity<List<MovieResponse>> findByDirector(@RequestParam String director) {
        return ResponseEntity.ok(service.findByDirector(director));
    }

    @GetMapping("/search/country")
    public ResponseEntity<List<MovieResponse>> findByCountry(@RequestParam String country) {
        return ResponseEntity.ok(service.findByCountry(country));
    }

    @GetMapping("/search/release")
    public ResponseEntity<List<MovieResponse>> findByRelease(@RequestParam LocalDate date) {
        return ResponseEntity.ok(service.findByRelease(date));
    }

    @GetMapping("/search/release/dates")
    public ResponseEntity<List<MovieResponse>> findByReleaseBetween(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        return ResponseEntity.ok(service.findByReleaseBetween(from, to));
    }

    @GetMapping("/search/genres")
    public ResponseEntity<List<MovieResponse>> findByGenreIds(@RequestBody List<Long> genreIds) {
        return ResponseEntity.ok(service.findByGenreIds(genreIds));
    }
}
