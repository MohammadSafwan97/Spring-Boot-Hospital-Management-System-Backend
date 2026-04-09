package com.safwantech.hms_backend.dto;

import com.safwantech.hms_backend.entity.type.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SignupRequestDto {
    private String username;
    private Long clinicId;
    private String password;
    @Email
    private String email;
    private Role role;
    private Boolean active;

}

