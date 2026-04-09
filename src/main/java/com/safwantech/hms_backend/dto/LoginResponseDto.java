package com.safwantech.hms_backend.dto;

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
}
