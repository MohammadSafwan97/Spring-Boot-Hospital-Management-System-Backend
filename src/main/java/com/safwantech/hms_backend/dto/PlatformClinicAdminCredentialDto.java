package com.safwantech.hms_backend.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PlatformClinicAdminCredentialDto {
    private Long clinicId;
    private String clinicName;
    private Long userId;
    private String username;
    private String temporaryPassword;
}

