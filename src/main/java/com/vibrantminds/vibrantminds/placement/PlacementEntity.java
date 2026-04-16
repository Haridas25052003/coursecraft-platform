package com.vibrantminds.vibrantminds.placement;

import com.vibrantminds.vibrantminds.candidate.CandidateEntity;
import com.vibrantminds.vibrantminds.company.CompanyEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "placement")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlacementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many placements → One candidate
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private CandidateEntity candidate;

    // Many placements → One company
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @Column(nullable = false)
    private String jobTitle;           // Role offered

    @Column(nullable = false)
    private String packageOffered;     // 3.5 LPA, 5 LPA etc

    @Column(nullable = false)
    private String joiningDate;

    @Column(nullable = false)
    private String placementType;
    // Campus, Off Campus, Referral etc

    private String location;           // Job location

    @Column(nullable = false)
    private String status = "PLACED";
    // PLACED → JOINED → LEFT → OFFER_DROPPED

    private String remarks;

    private boolean active = true;

    @Column(name = "placed_at")
    private LocalDateTime placedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.placedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}