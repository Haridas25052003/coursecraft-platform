package com.vibrantminds.vibrantminds.college.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CollegeRequestDto {

    @NotBlank(message = "College name is required")
    private String name;

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

    private String affiliatedTo;

    private String type;

    private boolean active = true;
}