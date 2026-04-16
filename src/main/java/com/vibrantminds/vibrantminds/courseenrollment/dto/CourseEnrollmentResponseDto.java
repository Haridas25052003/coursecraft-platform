package com.vibrantminds.vibrantminds.courseenrollment.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CourseEnrollmentResponseDto {

    private Long id;

    // Candidate details
    private Long candidateId;
    private String candidateName;
    private String candidateEmail;
    private String candidatePhone;

    // Course details
    private Long courseId;
    private String courseTitle;
    private String courseDuration;
    private String courseMode;
    private String courseFees;

    // Enrollment details
    private String status;
    private String paymentStatus;
    private String remarks;
    private LocalDateTime enrolledAt;
    private LocalDateTime completedAt;
    private LocalDateTime updatedAt;
}