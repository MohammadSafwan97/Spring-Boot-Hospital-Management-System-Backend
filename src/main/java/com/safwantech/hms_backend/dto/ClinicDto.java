package com.safwantech.hms_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClinicDto {
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 50)
    private String subdomain;

    @Email
    @Size(max = 120)
    private String email;

    @Size(max = 30)
    private String phone;

    @Size(max = 255)
    private String address;

    @Size(max = 255)
    private String logoUrl;

    @Size(max = 60)
    private String timezone;
    private Boolean active;
}
