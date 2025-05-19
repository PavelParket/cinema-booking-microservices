package com.booking.auth_service.mapper;

import com.booking.auth_service.dto.UserRequest;
import com.booking.auth_service.dto.UserResponse;
import com.booking.auth_service.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "role", ignore = true)
    User toEntity(UserRequest request);

    @Mapping(target = "password", ignore = true)
    UserResponse toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateUserEntity(UserRequest request, @MappingTarget User user);
}
