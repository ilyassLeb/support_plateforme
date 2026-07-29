package com.suppot.user_service.service;

import com.suppot.user_service.dto.UserRequestDto;
import com.suppot.user_service.dto.UserResponseDto;
import com.suppot.user_service.entity.User;

import java.util.List;


import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto requestDto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long id);
}
