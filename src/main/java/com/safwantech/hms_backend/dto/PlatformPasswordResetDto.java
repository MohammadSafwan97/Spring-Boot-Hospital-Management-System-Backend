package com.safwantech.hms_backend.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PlatformPasswordResetDto {
    private String temporaryPassword;
}

