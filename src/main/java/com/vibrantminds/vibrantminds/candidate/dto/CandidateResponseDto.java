package com.vibrantminds.vibrantminds.candidate.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CandidateResponseDto {

    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String qualification;
    private String skills;
    private String experience;
    private String currentCity;
    private String resumeLink;
    private String linkedinProfile;
    private String githubProfile;
    private String role;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}