package com.safwantech.hms_backend.dto;

import com.safwantech.hms_backend.entity.type.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter

public class UserDto {
        private Long id;
        private String name;
        private String email;
        private String password;
        private Role role;
        private boolean active;
}
