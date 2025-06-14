package com.booking.auth_service.dto;

import com.booking.auth_service.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    private LocalDateTime createdAt;

    private Role role;

    private String accessToken;

    private String refreshToken;
}
