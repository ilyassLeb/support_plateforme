package com.suppot.user_service.dto;


public record AuthResponseDto(
        String token,
        Long userId,
        String email,
        String role
) {}