package com.safwantech.hms_backend.dto;

import com.safwantech.hms_backend.entity.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDto {
    private Long id;
    private Long clinicId;
    private String username;
    private Role role;
    private boolean active;
}
