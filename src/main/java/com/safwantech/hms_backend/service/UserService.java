package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.UserDto;
import com.safwantech.hms_backend.entity.User;
import com.safwantech.hms_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /* ---------------- GET ALL USERS ---------------- */

    public List<UserDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    /* ---------------- CREATE USER ---------------- */

    public UserDto createUser(UserDto dto) {

        // Convert DTO → Entity
        User user = modelMapper.map(dto, User.class);

        User savedUser = userRepository.save(user);

        // Convert Entity → DTO
        return modelMapper.map(savedUser, UserDto.class);
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
}