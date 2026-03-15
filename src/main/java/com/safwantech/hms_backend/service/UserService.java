package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.UserDto;
import com.safwantech.hms_backend.dto.UserResponseDto;
import com.safwantech.hms_backend.entity.User;
import com.safwantech.hms_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    /* ---------------- GET ALL USERS ---------------- */

    public List<UserDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    /* ---------------- CREATE USER ---------------- */

    public UserResponseDto createUser(UserDto dto) {

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .active(dto.isActive())
                .build();

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    /* ---------------- UPDATE USER ---------------- */

    public UserDto updateUser(Long id, UserDto dto) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existingUser.setUsername(dto.getUsername());
        existingUser.setEmail(dto.getEmail());
        existingUser.setRole(dto.getRole());

        User updatedUser = userRepository.save(existingUser);

        return modelMapper.map(updatedUser, UserDto.class);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDto findByUsername(String name) {
        User user = userRepository.findByUsername(name).orElseThrow();
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return modelMapper.map(user, UserDto.class);
    }

}