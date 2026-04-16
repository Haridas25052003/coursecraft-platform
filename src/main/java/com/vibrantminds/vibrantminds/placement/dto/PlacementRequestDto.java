package com.vibrantminds.vibrantminds.placement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlacementRequestDto {

    @NotNull(message = "Candidate ID is required")
    private Long candidateId;

    @NotNull(message = "Company ID is required")
    private Long companyId;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @NotBlank(message = "Package offered is required")
    private String packageOffered;

    @NotBlank(message = "Joining date is required")
    private String joiningDate;

    @NotBlank(message = "Placement type is required")
    private String placementType;

    private String location;

    private String status = "PLACED";

    private String remarks;

    private boolean active = true;
}