package com.booking.booking_service.mapper;

import com.booking.booking_service.dto.SeatRequest;
import com.booking.booking_service.dto.SeatResponse;
import com.booking.booking_service.entity.Seat;
import com.booking.booking_service.entity.SeatTemplate;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "screening", ignore = true)
    Seat toEntity(SeatRequest seatRequest);

    @Mapping(target = "screeningId", expression = "java(seat.getScreening().getId())")
    SeatResponse toResponse(Seat seat);

    List<SeatResponse> toResponseList(List<Seat> seatList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "screening", ignore = true)
    void updateEntityFromRequest(SeatRequest request, @MappingTarget Seat seat);

    default List<Seat> toEntityList(List<SeatTemplate> seatTemplateList) {
        return seatTemplateList.stream()
                .map(seatTemplate -> Seat.builder()
                        .seatNumber(seatTemplate.getSeatNumber())
                        .row(seatTemplate.getRow())
                        .available(seatTemplate.isAvailable())
                        .seatType(seatTemplate.getSeatType())
                        .priceMultiplier(seatTemplate.getPriceMultiplier())
                        .build())
                .toList();
    }
}
