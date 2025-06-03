package com.booking.booking_service.mapper;

import com.booking.booking_service.dto.ScreeningRequest;
import com.booking.booking_service.dto.ScreeningResponse;
import com.booking.booking_service.entity.Screening;
import com.booking.booking_service.entity.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScreeningMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "seats", ignore = true)
    Screening toEntity(ScreeningRequest screeningRequest);

    @Mapping(target = "seats", source = "seats")
    ScreeningResponse toResponse(Screening screening);

    List<ScreeningResponse> toResponseList(List<Screening> screeningList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "seats", ignore = true)
    void updateEntityFromRequest(ScreeningRequest screeningRequest, @MappingTarget Screening screening);

    default List<Long> mapSeatList(List<Seat> seatList) {
        return seatList.stream()
                .map(Seat::getId)
                .toList();
    }
}
