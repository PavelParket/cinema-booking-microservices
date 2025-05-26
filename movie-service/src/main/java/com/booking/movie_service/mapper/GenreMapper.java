package com.booking.movie_service.mapper;

import com.booking.movie_service.dto.GenreRequest;
import com.booking.movie_service.dto.GenreResponse;
import com.booking.movie_service.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    @Mapping(target = "id", ignore = true)
    Genre toEntity(GenreRequest genreRequest);

    GenreResponse toResponse(Genre genre);

    List<GenreResponse> toResponseList(List<Genre> genres);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(GenreRequest genreRequest, @MappingTarget Genre genre);
}
