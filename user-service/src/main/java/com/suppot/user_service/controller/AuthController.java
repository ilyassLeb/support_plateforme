package com.suppot.user_service.controller;

import com.suppot.user_service.dto.AuthResponseDto;
import com.suppot.user_service.dto.LoginRequestDto;
import com.suppot.user_service.entity.User;
import com.suppot.user_service.repository.UserRepository;
import com.suppot.user_service.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.suppot.user_service.dto.RegisterRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur : Cet email est déjà utilisé !");
        }

        String assignedRole = (request.role() != null && !request.role().isBlank())
                ? request.role()
                : "ROLE_USER";

        User newUser = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password())) // <-- BCrypt ici !
                .role(assignedRole)
                .build();

        User savedUser = userRepository.save(newUser);

        String token = jwtUtils.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getRole());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponseDto(token, savedUser.getId(), savedUser.getEmail(), savedUser.getRole()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Mot de passe incorrect");
        }

        String token = jwtUtils.generateToken(user.getId(), user.getEmail(), user.getRole());

        return ResponseEntity.ok(new AuthResponseDto(token, user.getId(), user.getEmail(), user.getRole()));
    }
}
