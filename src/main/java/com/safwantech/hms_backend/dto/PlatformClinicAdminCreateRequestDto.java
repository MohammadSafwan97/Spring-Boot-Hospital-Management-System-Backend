package com.safwantech.hms_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PlatformClinicAdminCreateRequestDto {
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;
}

