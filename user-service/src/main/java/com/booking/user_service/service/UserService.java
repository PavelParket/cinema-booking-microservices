package com.booking.user_service.service;

import com.booking.user_service.dto.UserRequest;
import com.booking.user_service.dto.UserResponse;
import com.booking.user_service.entity.User;
import com.booking.user_service.mapper.UserMapper;
import com.booking.user_service.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    @Transactional
    public UserResponse create(UserRequest request) {
        User user = mapper.toEntity(request);

        return mapper.toResponse(repository.save(user));
    }

    @Transactional
    public UserResponse update(UUID id, UserRequest request) {
        User user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User id=" + id + " not found"));

        mapper.updateUserEntity(request, user);

        return mapper.toResponse(repository.save(user));
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("User id=" + id + " not found");
        }

        repository.deleteById(id);
    }

    public List<UserResponse> getAll() {
        return mapper.toListResponse(repository.findAll());
    }

    public UserResponse getById(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User id=" + id + " not found")));
    }
}
