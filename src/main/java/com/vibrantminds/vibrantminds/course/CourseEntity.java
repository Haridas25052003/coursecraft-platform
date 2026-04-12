package com.vibrantminds.vibrantminds.course;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;              // Java Full Stack, Software Testing

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false)
    private String duration;           // 3 Months, 6 Months

    @Column(nullable = false)
    private String mode;               // Online, Offline, Hybrid

    @Column(nullable = false)
    private String fees;               // 15000, Free etc

    @Column(nullable = false)
    private String syllabus;           // Topics covered

    @Column(nullable = false)
    private String eligibility;        // Any Graduate, B.E/B.Tech

    private String certificate;        // Yes / No

    private String jobAssistance;      // Yes / No

    private String thumbnail;          // image path or URL

    private boolean active = true;

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