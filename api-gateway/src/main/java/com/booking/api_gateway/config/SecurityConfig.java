package com.booking.api_gateway.config;

import com.booking.api_gateway.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers("/api/users/**").hasAnyRole("ADMIN", "SERVICE")
                        .pathMatchers("/api/movies/**").permitAll()
                        .pathMatchers("/api/genres/**").permitAll()
                        .pathMatchers("/api/bookings/**").hasAnyRole("USER", "ADMIN")
                        .pathMatchers("/api/screenings/**").permitAll()
                        .pathMatchers("/api/seats/**").permitAll()
                        .pathMatchers("/api/seat-templates/**").hasRole("ADMIN")
                        .anyExchange().authenticated())
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
