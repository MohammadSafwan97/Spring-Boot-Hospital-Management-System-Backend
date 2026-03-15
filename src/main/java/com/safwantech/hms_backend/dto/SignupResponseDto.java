package com.safwantech.hms_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

@Data
public class SignupResponseDto {
    private Long id;
    private String username;
}
