package com.vibrantminds.vibrantminds.course.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseRequestDto {

    @NotBlank(message = "Course title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Duration is required")
    private String duration;

    @NotBlank(message = "Mode is required")
    private String mode;

    @NotBlank(message = "Fees is required")
    private String fees;

    @NotBlank(message = "Syllabus is required")
    private String syllabus;

    @NotBlank(message = "Eligibility is required")
    private String eligibility;

    private String certificate;

    private String jobAssistance;

    private String thumbnail;

    private boolean active = true;
}