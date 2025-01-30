package com.dwes.empleadosapi.controllers;

import com.dwes.empleadosapi.DTO.LoginRequestDTO;
import com.dwes.empleadosapi.DTO.LoginResponseDTO;
import com.dwes.empleadosapi.DTO.UserRegistrerDTO;
import com.dwes.empleadosapi.config.JwtTokenProvider;
import com.dwes.empleadosapi.entities.UserEntity;
import com.dwes.empleadosapi.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserEntityRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/registrer")
    public UserEntity save(@RequestBody UserRegistrerDTO userDTO) {
        return this.userRepository.save(UserEntity.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .build());

    }

    @PostMapping("/auth/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginDTO) {

    }
}
