package com.vibrantminds.vibrantminds.candidate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CandidateRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Mobile is required")
    @Size(min = 10, max = 10, message = "Enter valid 10 digit mobile number")
    private String mobile;

    private String qualification;

    private String skills;

    private String experience;

    private String currentCity;

    private String resumeLink;

    private String linkedinProfile;

    private String githubProfile;
}