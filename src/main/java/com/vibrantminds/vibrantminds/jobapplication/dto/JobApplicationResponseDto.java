package com.vibrantminds.vibrantminds.jobapplication.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class JobApplicationResponseDto {

    private Long id;

    // Candidate details
    private Long candidateId;
    private String candidateName;
    private String candidateEmail;
    private String candidatePhone;

    // Job details
    private Long jobId;
    private String jobTitle;
    private String companyName;
    private String jobLocation;

    // Application details
    private String status;
    private String coverLetter;
    private String resumePath;
    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;
}