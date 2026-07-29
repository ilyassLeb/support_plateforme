package com.support.ticket_service.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDto {

    private Long id;
    private String fullName;
    private String email;
    private String role;
    private LocalDateTime createdAt;
}
