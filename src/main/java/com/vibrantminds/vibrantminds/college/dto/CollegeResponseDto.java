package com.vibrantminds.vibrantminds.college.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CollegeResponseDto {

    private Long id;
    private String name;
    private String city;
    private String state;
    private String address;
    private String contactPerson;
    private String contactEmail;
    private String contactPhone;
    private String website;
    private String logo;
    private String description;
    private String affiliatedTo;
    private String type;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}