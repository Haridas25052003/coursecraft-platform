package com.vibrantminds.vibrantminds.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminResponseDto {

    private Long id;
    private String name;
    private String email;
    private String role;
    private LocalDateTime createdAt;
}
