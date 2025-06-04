package com.booking.movie_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequest {

    private String title;

    private Integer durationMinutes;

    private String director;

    private LocalDate release;

    private String country;

    private Integer ageRating;

    private List<Long> genreIds;
}
