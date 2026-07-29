package com.suppot.user_service.service.impl;
import com.suppot.user_service.dto.UserRequestDto;
import com.suppot.user_service.dto.UserResponseDto;
import com.suppot.user_service.entity.User;
import com.suppot.user_service.exception.ResourceNotFoundException;
import com.suppot.user_service.mapper.UserMapper;
import com.suppot.user_service.repository.UserRepository;
import com.suppot.user_service.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto createUser(UserRequestDto requestDto) {
        User user = UserMapper.toEntity(requestDto);
        User savedUser = userRepository.save(user);
        return UserMapper.toResponseDto(savedUser);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDto)
                .toList();
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return UserMapper.toResponseDto(user);
    }
}
