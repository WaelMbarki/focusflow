package com.focusflow.demo.service;

import com.focusflow.demo.dto.UserDTO;
import com.focusflow.demo.exception.ResourceNotFoundException;
import com.focusflow.demo.model.User;
import com.focusflow.demo.repository.UserRepository;
import com.focusflow.demo.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserDTO getCurrentUserProfile() {
        String currentUserId = SecurityUtils.getCurrentUserId();

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return toDTO(user);
    }
    public UserDTO updateUser(String fieldName, String field)
    {
        String currentUserId = SecurityUtils.getCurrentUserId();

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if(fieldName.equals("email"))
        {
            if(userRepository.findByEmail(field).isEmpty())
            {
                user.setEmail(field);
            }

        }
        if(fieldName.equals("password"))
        {
            user.setPasswordHash(passwordEncoder.encode(field));
        }
        if(fieldName.equals("name"))
        {
            user.setFullName(field);
        }
        User saved = userRepository.save(user);
        return toDTO(saved);
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getFullName(), user.getBirthDate());
    }
}