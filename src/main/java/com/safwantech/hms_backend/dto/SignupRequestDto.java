package com.safwantech.hms_backend.dto;

import com.safwantech.hms_backend.entity.type.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
    private Role role;
    private Boolean active;

}

