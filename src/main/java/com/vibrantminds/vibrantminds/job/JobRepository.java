package com.vibrantminds.vibrantminds.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {

    // Get all active jobs
    List<JobEntity> findByActiveTrue();

    // Search jobs by title
    List<JobEntity> findByTitleContainingIgnoreCase(String title);

    // Search jobs by location
    List<JobEntity> findByLocationContainingIgnoreCase(String location);

    // Search jobs by job type
    List<JobEntity> findByJobType(String jobType);

    // Search jobs by company
    List<JobEntity> findByCompanyNameContainingIgnoreCase(String companyName);
}