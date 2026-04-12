package com.vibrantminds.vibrantminds.course.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CourseResponseDto {

    private Long id;
    private String title;
    private String description;
    private String duration;
    private String mode;
    private String fees;
    private String syllabus;
    private String eligibility;
    private String certificate;
    private String jobAssistance;
    private String thumbnail;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}