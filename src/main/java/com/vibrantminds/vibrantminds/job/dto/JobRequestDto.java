package com.vibrantminds.vibrantminds.job.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JobRequestDto {

    @NotBlank(message = "Job title is required")
    private String title;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Job type is required")
    private String jobType;

    @NotBlank(message = "Experience is required")
    private String experience;

    @NotBlank(message = "Salary is required")
    private String salary;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Skills are required")
    private String skills;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    private String lastDateToApply;

    private boolean active = true;
}