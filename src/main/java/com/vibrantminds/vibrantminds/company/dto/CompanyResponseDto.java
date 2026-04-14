package com.vibrantminds.vibrantminds.company.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CompanyResponseDto {

    private Long id;
    private String name;
    private String industry;
    private String city;
    private String state;
    private String address;
    private String contactPerson;
    private String contactEmail;
    private String contactPhone;
    private String website;
    private String logo;
    private String description;
    private String companySize;
    private String foundedYear;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}