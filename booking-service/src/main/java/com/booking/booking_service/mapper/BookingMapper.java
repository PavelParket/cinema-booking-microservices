package com.booking.booking_service.mapper;

import com.booking.booking_service.dto.BookingRequest;
import com.booking.booking_service.dto.BookingResponse;
import com.booking.booking_service.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "screening", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "price", ignore = true)
    Booking toEntity(BookingRequest bookingRequest);

    @Mapping(target = "screeningId", source = "screening.id")
    BookingResponse toResponse(Booking booking);

    List<BookingResponse> toResponseList(List<Booking> bookingList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "screening", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "price", ignore = true)
    void updateEntityFromRequest(BookingRequest bookingRequest, @MappingTarget Booking booking);
}
