package com.support.ticket_service.service.client;


import com.support.ticket_service.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {

    @Override
    public UserResponseDto getUserById(Long id) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(null);
        return dto;
    }
}
