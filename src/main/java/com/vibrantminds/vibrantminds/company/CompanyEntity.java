package com.vibrantminds.vibrantminds.company;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "company")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;               // Company name

    @Column(nullable = false)
    private String industry;           // IT, Finance, Healthcare etc

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    private String address;

    private String contactPerson;      // HR name

    private String contactEmail;

    private String contactPhone;

    private String website;

    private String logo;               // logo image path

    @Column(length = 1000)
    private String description;

    private String companySize;        // Startup, SME, MNC etc

    private String foundedYear;

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