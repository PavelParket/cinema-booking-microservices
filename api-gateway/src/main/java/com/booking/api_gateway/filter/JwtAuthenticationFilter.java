package com.booking.api_gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements WebFilter {

   @Value("${app.jwt.secret}")
   private String jwtSecret;

   @Override
   public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
      String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
         return chain.filter(exchange);
      }

      String token = authHeader.substring(7);
      SecretKey key = io.jsonwebtoken.security.Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

      try {
         Claims claims = Jwts.parserBuilder()
               .setSigningKey(key)
               .build()
               .parseClaimsJws(token)
               .getBody();

         String username = claims.getSubject();
         String role = claims.get("role", String.class);

         UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
               username,
               null,
               List.of(new SimpleGrantedAuthority("ROLE_" + role)));

         return chain.filter(exchange)
               .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
      } catch (Exception e) {
         exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
         return exchange.getResponse().setComplete();
      }
   }
}