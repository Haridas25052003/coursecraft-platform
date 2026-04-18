package com.vibrantminds.vibrantminds.testimonial.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TestimonialResponseDto {

    private Long id;

    // Person details
    private Long candidateId;
    private String personName;
    private String personPhoto;
    private String personDesignation;
    private String companyName;

    // Testimonial details
    private String message;
    private int rating;
    private String course;
    private boolean active;
    private boolean featured;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}