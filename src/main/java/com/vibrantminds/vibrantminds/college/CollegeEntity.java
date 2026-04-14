package com.vibrantminds.vibrantminds.college;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "college")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollegeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;               // College name

    @Column(nullable = false)
    private String city;               // Pune, Mumbai etc

    @Column(nullable = false)
    private String state;              // Maharashtra etc

    private String address;

    private String contactPerson;      // TPO name

    private String contactEmail;

    private String contactPhone;

    private String website;

    private String logo;               // logo image path

    @Column(length = 1000)
    private String description;

    private String affiliatedTo;       // Savitribai Phule etc

    private String type;               // Engineering, Management etc

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