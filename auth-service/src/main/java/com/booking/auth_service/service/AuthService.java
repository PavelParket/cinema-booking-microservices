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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository repository;

    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse create(UserRequest request) {
        validateUserRequest(request);

        User user = mapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        return mapper.toResponse(repository.save(user));
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

        return mapper.toResponse(updatedUser);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new UserException("User not found with id: " + id);
        }

        repository.deleteById(id);
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
}
