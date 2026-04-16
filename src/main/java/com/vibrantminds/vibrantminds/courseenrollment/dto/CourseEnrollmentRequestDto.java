package com.vibrantminds.vibrantminds.courseenrollment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseEnrollmentRequestDto {

    @NotNull(message = "Course ID is required")
    private Long courseId;

    private String remarks;
}