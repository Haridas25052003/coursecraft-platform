package com.vibrantminds.vibrantminds.jobapplication;

import com.vibrantminds.vibrantminds.jobapplication.dto.JobApplicationRequestDto;
import com.vibrantminds.vibrantminds.jobapplication.dto.JobApplicationResponseDto;
import java.util.List;

public interface JobApplicationService {

    // Candidate operations
    JobApplicationResponseDto applyForJob(
            String candidateEmail,
            JobApplicationRequestDto dto);

    List<JobApplicationResponseDto> getMyApplications(
            String candidateEmail);

    void withdrawApplication(String candidateEmail, Long applicationId);

    // Admin operations
    List<JobApplicationResponseDto> getAllApplications();

    List<JobApplicationResponseDto> getApplicationsByJob(Long jobId);

    List<JobApplicationResponseDto> getApplicationsByStatus(
            String status);

    JobApplicationResponseDto updateApplicationStatus(
            Long applicationId, String status);

    JobApplicationResponseDto getApplicationById(Long id);

    // Stats
    long countApplicationsByJob(Long jobId);
}