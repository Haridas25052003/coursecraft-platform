package com.vibrantminds.vibrantminds.candidate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "candidate")
@Data
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String role = "ROLE_CANDIDATE";
}