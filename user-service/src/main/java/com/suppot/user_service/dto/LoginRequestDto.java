package com.suppot.user_service.dto;


public record LoginRequestDto(
        String email,
        String password
) {}