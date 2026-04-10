package com.safwantech.hms_backend.dto;

import com.safwantech.hms_backend.entity.type.Role;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthUserDto {
    private Long id;
    private Long clinicId;
    private String username;
    private String email;
    private Role role;
    private boolean active;
}

