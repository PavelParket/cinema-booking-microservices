package com.booking.movie_service.mapper;

import com.booking.movie_service.dto.MovieRequest;
import com.booking.movie_service.dto.MovieResponse;
import com.booking.movie_service.entity.Genre;
import com.booking.movie_service.entity.Movie;
import com.booking.movie_service.resolver.GenreResolver;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = GenreMapper.class)
public interface MovieMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "genres", source = "genreIds", qualifiedByName = "mapGenreIdsToGenres")
    Movie toEntity(MovieRequest movieRequest, @Context GenreResolver genreResolver);

    MovieResponse toResponse(Movie movie);

    List<MovieResponse> toResponseList(List<Movie> movies);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "genres", source = "genreIds", qualifiedByName = "mapGenreIdsToGenres")
    void updateEntityFromRequest(MovieRequest movieRequest, @MappingTarget Movie movie, @Context GenreResolver genreResolver);

    @Named("mapGenreIdsToGenres")
    default Set<Genre> mapGenreIdsToGenres(List<Long> ids, @Context GenreResolver genreResolver) {
        return new HashSet<>(genreResolver.resolveGenresByIds(ids));
    }
}
