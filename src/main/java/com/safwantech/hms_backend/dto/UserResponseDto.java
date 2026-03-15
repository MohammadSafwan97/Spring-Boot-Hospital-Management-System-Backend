package com.safwantech.hms_backend.dto;

import com.safwantech.hms_backend.entity.type.Role;

public class UserResponseDto {
    private Long id;
    private String username;
    private Role role;
    private boolean active;
}
