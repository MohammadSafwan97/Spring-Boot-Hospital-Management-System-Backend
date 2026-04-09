package com.safwantech.hms_backend.dto;

import com.safwantech.hms_backend.entity.type.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponseDto {
    private String jwt;
    private Long id;
    private Long clinicId;
    private Role role;
}
