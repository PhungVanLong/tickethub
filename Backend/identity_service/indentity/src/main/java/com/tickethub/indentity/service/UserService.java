package com.tickethub.indentity.service;

import com.tickethub.indentity.dto.UserDtos;
import com.tickethub.indentity.entity.User;
import com.tickethub.indentity.events.UserUpdatedEvent;
import com.tickethub.indentity.mappers.UserMapper;
import com.tickethub.indentity.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public UserService(UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDtos.UserResponse getMe(String email) {
        return UserMapper.toResponse(getByEmail(email));
    }

    public UserDtos.UserResponse updateMe(String email, UserDtos.UpdateMeRequest request) {
        User user = getByEmail(email);
        user.setFullName(request.fullName());
        user.setPhone(request.phone());
        user.setAvatarUrl(request.avatarUrl());
        User saved = userRepository.save(user);
        eventPublisher.publishEvent(new UserUpdatedEvent(saved.getId()));
        return UserMapper.toResponse(saved);
    }

    public UserDtos.UserResponse getById(UUID userId) {
        return UserMapper.toResponse(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
    }
}
