package com.vibrantminds.vibrantminds.job;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "job")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String jobType;        // Full Time, Part Time, Internship

    @Column(nullable = false)
    private String experience;     // Fresher, 1-2 years etc

    @Column(nullable = false)
    private String salary;         // 3-5 LPA etc

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false)
    private String skills;         // Java, Spring Boot, MySQL etc

    @Column(nullable = false)
    private String qualification;  // B.E, B.Tech, MCA etc

    private String lastDateToApply;

    private boolean active = true; // Admin can activate/deactivate

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}