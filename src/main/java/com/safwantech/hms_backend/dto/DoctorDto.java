package com.safwantech.hms_backend.dto;


import lombok.Getter;

import lombok.Setter;

@Getter
@Setter

public class DoctorDto {

    private Long id;
    private Long clinicId;
    private String name;
    private String specialization;
    private Integer experience;
    private String phoneNo;
    private String email;
}

