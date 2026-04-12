package com.vibrantminds.vibrantminds.candidate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository
        extends JpaRepository<CandidateEntity, Long> {

    Optional<CandidateEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    // Admin - search candidates
    List<CandidateEntity> findByNameContainingIgnoreCase(String name);

    List<CandidateEntity> findByCurrentCity(String city);

    List<CandidateEntity> findBySkillsContainingIgnoreCase(String skill);

    List<CandidateEntity> findByActiveTrue();
}