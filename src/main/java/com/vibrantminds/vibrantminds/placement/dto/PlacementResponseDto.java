package com.vibrantminds.vibrantminds.placement.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PlacementResponseDto {

    private Long id;

    // Candidate details
    private Long candidateId;
    private String candidateName;
    private String candidateEmail;
    private String candidatePhone;

    // Company details
    private Long companyId;
    private String companyName;
    private String companyIndustry;
    private String companyCity;

    // Placement details
    private String jobTitle;
    private String packageOffered;
    private String joiningDate;
    private String placementType;
    private String location;
    private String status;
    private String remarks;
    private boolean active;
    private LocalDateTime placedAt;
    private LocalDateTime updatedAt;
}