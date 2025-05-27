package com.booking.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

   @Bean
   public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) {
      return httpSecurity
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchange -> exchange
                  .pathMatchers("/api/auth/**").permitAll()
                  .pathMatchers("/api/user/**").permitAll()
                  .pathMatchers("/api/movies/**").permitAll()
                  .pathMatchers("/api/genres/**").permitAll()
                  .anyExchange().permitAll())
            /*
             * .oauth2ResourceServer(oauth2 -> oauth2
             * .jwt(jwtSpec -> jwtSpec
             * .jwkSetUri("http://localhost:9000/.well-known/jwks.json")
             * )
             * )
             */
            .build();
   }
}
