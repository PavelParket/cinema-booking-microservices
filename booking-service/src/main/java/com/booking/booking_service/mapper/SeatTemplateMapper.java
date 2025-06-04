package com.booking.booking_service.mapper;

import com.booking.booking_service.dto.SeatTemplateRequest;
import com.booking.booking_service.dto.SeatTemplateResponse;
import com.booking.booking_service.entity.SeatTemplate;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatTemplateMapper {

    @Mapping(target = "id", ignore = true)
    SeatTemplate toEntity(SeatTemplateRequest seatTemplateRequest);

    SeatTemplateResponse toResponse(SeatTemplate seatTemplate);

    List<SeatTemplateResponse> toResponseList(List<SeatTemplate> seatTemplate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(SeatTemplateRequest seatTemplateRequest, @MappingTarget SeatTemplate seatTemplate);
}
