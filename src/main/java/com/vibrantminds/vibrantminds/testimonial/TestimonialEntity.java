package com.vibrantminds.vibrantminds.testimonial;

import com.vibrantminds.vibrantminds.candidate.CandidateEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "testimonial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many testimonials → One candidate
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private CandidateEntity candidate;

    // If testimonial is added manually by admin
    // (not linked to any candidate)
    private String personName;

    private String personPhoto;

    private String personDesignation;  // Java Developer at TCS

    private String companyName;        // Where they got placed

    @Column(nullable = false, length = 1000)
    private String message;            // Testimonial message

    private int rating;                // 1 to 5

    private String course;             // Course they completed

    private boolean active = true;

    private boolean featured = false;  // Show on homepage

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