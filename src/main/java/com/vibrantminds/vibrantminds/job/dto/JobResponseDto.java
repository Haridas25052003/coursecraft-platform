package com.vibrantminds.vibrantminds.job.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class JobResponseDto {

    private Long id;
    private String title;
    private String companyName;
    private String location;
    private String jobType;
    private String experience;
    private String salary;
    private String description;
    private String skills;
    private String qualification;
    private String lastDateToApply;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}