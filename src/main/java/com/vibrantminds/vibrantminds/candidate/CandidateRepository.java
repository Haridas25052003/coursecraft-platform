package com.vibrantminds.vibrantminds.candidate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CandidateRepository 
        extends JpaRepository<CandidateEntity, Long> {
    Optional<CandidateEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}