package com.focusflow.demo.service;

import com.focusflow.demo.dto.AuthResponseDTO;
import com.focusflow.demo.dto.LoginDTO;
import com.focusflow.demo.dto.RegisterDTO;
import com.focusflow.demo.dto.UserDTO;
import com.focusflow.demo.exception.EmailAlreadyExistsException;
import com.focusflow.demo.exception.InvalidCredentialsException;
import com.focusflow.demo.exception.ResourceNotFoundException;
import com.focusflow.demo.model.User;
import com.focusflow.demo.repository.UserRepository;
import com.focusflow.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponseDTO register(RegisterDTO registerdto) {
        if (userRepository.findByEmail(registerdto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user = new User();
        user.setEmail(registerdto.getEmail());
        user.setBirthDate(registerdto.getBirthdate());
        user.setFullName(registerdto.getName());
        user.setPasswordHash(passwordEncoder.encode(registerdto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        User saved = userRepository.save(user);

        String token = jwtUtil.generateToken(String.valueOf(saved.getId()));
        return new AuthResponseDTO(token, null, toDTO(saved));
    }
    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponseDTO login(LoginDTO loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPasswordHash())) {
            throw new ResourceNotFoundException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getId());
        return new AuthResponseDTO(token, null,toDTO(user));
    }
    public UserDTO toDTO(User user)
    {
        return new UserDTO(user.getId(),user.getEmail(),user.getFullName(),user.getBirthDate());
    }

}

