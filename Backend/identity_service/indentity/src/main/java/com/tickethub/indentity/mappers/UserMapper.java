package com.tickethub.indentity.mappers;

import com.tickethub.indentity.dto.UserDtos;
import com.tickethub.indentity.entity.User;

public final class UserMapper {
    private UserMapper() {
    }

    public static UserDtos.UserResponse toResponse(User user) {
        return new UserDtos.UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPhone(),
                user.getAvatarUrl(),
                user.getRole(),
                user.isVerified(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
