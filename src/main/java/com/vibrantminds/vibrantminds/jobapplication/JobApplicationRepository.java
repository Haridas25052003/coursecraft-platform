package com.vibrantminds.vibrantminds.jobapplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository
        extends JpaRepository<JobApplicationEntity, Long> {

    // Get all applications by candidate
    List<JobApplicationEntity> findByCandidateId(Long candidateId);

    // Get all applications for a job
    List<JobApplicationEntity> findByJobId(Long jobId);

    // Get all applications by status
    List<JobApplicationEntity> findByStatus(String status);

    // Check if candidate already applied for a job
    boolean existsByCandidateIdAndJobId(Long candidateId, Long jobId);

    // Get specific application
    Optional<JobApplicationEntity> findByCandidateIdAndJobId(
            Long candidateId, Long jobId);

    // Count applications per job
    long countByJobId(Long jobId);
}