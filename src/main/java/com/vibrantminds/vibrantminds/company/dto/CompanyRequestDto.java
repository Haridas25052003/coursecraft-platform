package com.vibrantminds.vibrantminds.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyRequestDto {

    @NotBlank(message = "Company name is required")
    private String name;

    @NotBlank(message = "Industry is required")
    private String industry;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    private String address;

    private String contactPerson;

    private String contactEmail;

    private String contactPhone;

    private String website;

    private String logo;

    private String description;

    private String companySize;

    private String foundedYear;

    private boolean active = true;
}