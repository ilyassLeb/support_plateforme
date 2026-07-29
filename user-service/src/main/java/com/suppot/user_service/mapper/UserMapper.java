package com.suppot.user_service.mapper;



import com.suppot.user_service.dto.UserRequestDto;
import com.suppot.user_service.dto.UserResponseDto;
import com.suppot.user_service.entity.User;

import java.time.LocalDateTime;

public class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(UserRequestDto dto) {
        return User.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .role(dto.getRole())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static UserResponseDto toResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
