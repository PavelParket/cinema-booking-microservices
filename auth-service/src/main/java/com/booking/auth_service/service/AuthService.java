package com.booking.auth_service.service;

import com.booking.auth_service.dto.UserRequest;
import com.booking.auth_service.dto.UserResponse;
import com.booking.auth_service.entity.User;
import com.booking.auth_service.exception.UserException;
import com.booking.auth_service.mapper.UserMapper;
import com.booking.auth_service.repository.AuthRepository;
import com.booking.auth_service.util.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository repository;

    private final UserMapper mapper;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    private final WebClient webClient;

    @Transactional
    public UserResponse create(UserRequest request) {
        validateUserRequest(request);

        User user = mapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        Map<String, Object> external = Map.of(
                "username", user.getUsername(),
                "email", user.getEmail()
        );

        try {
            String technicalToken = tokenService.generateAccessToken("auth-service", "SERVICE");

            Object response = webClient.post()
                    .uri("http://localhost:8888/api/users")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + technicalToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(external)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse ->
                            clientResponse.bodyToMono(String.class)
                                    .flatMap(errorBody ->
                                            Mono.error(new RuntimeException("External service error: " + errorBody)))
                    )
                    .bodyToMono(Object.class)
                    .block();

            System.out.println(response);

        } catch (WebClientResponseException ex) {
            throw new RuntimeException("External service error: " + ex.getResponseBodyAsString(), ex);
        } catch (Exception ex) {
            throw new RuntimeException("Unexpected external call error", ex);
        }

        User savedUser = repository.save(user);

        String accessToken = tokenService.generateAccessToken(savedUser.getUsername(), savedUser.getRole().name());
        String refreshToken = tokenService.generateRefreshToken(savedUser.getUsername(), savedUser.getRole().name());

        return mapper.toResponse(savedUser, accessToken, refreshToken);
    }

    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserException("User not found with id: " + id));

        validateUserRequest(request);

        mapper.updateUserEntity(request, user);

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updatedUser = repository.save(user);

        Map<String, Object> external = Map.of(
                "username", updatedUser.getUsername(),
                "email", updatedUser.getEmail()
        );

        try {
            Object response = webClient.put()
                    .uri("http://localhost:8888/api/users/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(external)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse ->
                            clientResponse.bodyToMono(String.class)
                                    .flatMap(errorBody ->
                                            Mono.error(new RuntimeException("External update error: " + errorBody)))
                    )
                    .bodyToMono(Object.class)
                    .block();

            System.out.println(response);

        } catch (WebClientResponseException ex) {
            throw new RuntimeException("External service update error: " + ex.getResponseBodyAsString(), ex);
        } catch (Exception ex) {
            throw new RuntimeException("Unexpected error during external update", ex);
        }

        return mapper.toResponse(updatedUser);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new UserException("User not found with id: " + id);
        }

        repository.deleteById(id);

        try {
            webClient.delete()
                    .uri("http://user-service/users/{id}", id)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse ->
                            clientResponse.bodyToMono(String.class)
                                    .flatMap(errorBody ->
                                            Mono.error(new RuntimeException("External delete error: " + errorBody)))
                    )
                    .toBodilessEntity()
                    .block();

        } catch (WebClientResponseException ex) {
            throw new RuntimeException("External service delete error: " + ex.getResponseBodyAsString(), ex);
        } catch (Exception ex) {
            throw new RuntimeException("Unexpected error during external delete", ex);
        }
    }

    public List<UserResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public UserResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new UserException("User not found with id: " + id));
    }

    public UserResponse getByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toResponse)
                .orElseThrow(() -> new UserException("User not found with email: " + email));
    }

    private void validateUserRequest(UserRequest request) {
        if (request.getEmail() != null && repository.existsByEmail(request.getEmail())) {
            throw new UserException("Email already exists: " + request.getEmail());
        }
    }

    public UserResponse login(UserRequest request) {
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException("Invalid email"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserException("Invalid password");
        }

        String accessToken = tokenService.generateAccessToken(user.getUsername(), user.getRole().name());
        String refreshToken = tokenService.generateRefreshToken(user.getUsername(), user.getRole().name());

        return mapper.toResponse(user, accessToken, refreshToken);
    }

    public Boolean check(String token) {
        return tokenService.validateToken(token);
    }
}
