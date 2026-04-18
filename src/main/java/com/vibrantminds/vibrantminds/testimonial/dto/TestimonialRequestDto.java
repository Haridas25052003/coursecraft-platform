package com.vibrantminds.vibrantminds.testimonial.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TestimonialRequestDto {

    // Optional - if linked to a candidate
    private Long candidateId;

    // Required if no candidateId
    private String personName;

    private String personPhoto;

    private String personDesignation;

    private String companyName;

    @NotBlank(message = "Message is required")
    private String message;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating = 5;

    private String course;

    private boolean active = true;

    private boolean featured = false;
}