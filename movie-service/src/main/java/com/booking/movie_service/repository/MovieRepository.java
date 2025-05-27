package com.booking.movie_service.repository;

import com.booking.movie_service.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    List<Movie> findByTitle(String title);

    List<Movie> findByDirector(String director);

    List<Movie> findByCountry(String country);

    List<Movie> findByRelease(LocalDate date);

    List<Movie> findByReleaseBetween(LocalDate from, LocalDate to);

    List<Movie> findByGenresIdIn(List<Long> genreIds);
}