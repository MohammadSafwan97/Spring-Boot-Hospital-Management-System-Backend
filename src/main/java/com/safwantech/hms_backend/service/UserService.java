package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.UserDto;
import com.safwantech.hms_backend.dto.UserResponseDto;
import com.safwantech.hms_backend.entity.Clinic;
import com.safwantech.hms_backend.entity.User;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.ClinicRepository;
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
    private final ClinicRepository clinicRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers(Long clinicId) {
        return userRepository.findByClinicId(clinicId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public UserResponseDto createUser(UserDto dto) {
        User user = User.builder()
                .clinic(getClinic(dto.getClinicId()))
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .active(dto.isActive())
                .build();

        return mapToResponseDto(userRepository.save(user));
    }

    public UserDto updateUser(Long clinicId, Long id, UserDto dto) {
        User existingUser = userRepository.findByIdAndClinicId(id, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id + " for clinic: " + clinicId));

        existingUser.setUsername(dto.getUsername());
        existingUser.setEmail(dto.getEmail());
        existingUser.setRole(dto.getRole());
        existingUser.setActive(dto.isActive());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return mapToDto(userRepository.save(existingUser));
    }

    public void deleteUser(Long clinicId, Long id) {
        User user = userRepository.findByIdAndClinicId(id, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id + " for clinic: " + clinicId));
        userRepository.delete(user);
    }

    public UserDto findByUsername(Long clinicId, String name) {
        User user = userRepository.findByUsernameAndClinicId(name, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + name + " for clinic: " + clinicId));
        return mapToDto(user);
    }

    public UserDto findById(Long clinicId, Long userId) {
        User user = userRepository.findByIdAndClinicId(userId, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId + " for clinic: " + clinicId));
        return mapToDto(user);
    }

    private Clinic getClinic(Long clinicId) {
        return clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + clinicId));
    }

    private UserDto mapToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setClinicId(user.getClinic().getId());
        userDto.setPassword(null);
        return userDto;
    }

    private UserResponseDto mapToResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .clinicId(user.getClinic().getId())
                .username(user.getUsername())
                .role(user.getRole())
                .active(Boolean.TRUE.equals(user.getActive()))
                .build();
    }
}
