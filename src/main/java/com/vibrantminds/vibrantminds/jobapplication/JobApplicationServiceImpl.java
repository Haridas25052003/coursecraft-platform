package com.vibrantminds.vibrantminds.jobapplication;

import com.vibrantminds.vibrantminds.candidate.CandidateEntity;
import com.vibrantminds.vibrantminds.candidate.CandidateRepository;
import com.vibrantminds.vibrantminds.exception.ResourceNotFoundException;
import com.vibrantminds.vibrantminds.exception.UnauthorizedException;
import com.vibrantminds.vibrantminds.job.JobEntity;
import com.vibrantminds.vibrantminds.job.JobRepository;
import com.vibrantminds.vibrantminds.jobapplication.dto.JobApplicationRequestDto;
import com.vibrantminds.vibrantminds.jobapplication.dto.JobApplicationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;

    //  Candidate applies for job
    @Override
    public JobApplicationResponseDto applyForJob(
            String candidateEmail,
            JobApplicationRequestDto dto) {

        // Get candidate
        CandidateEntity candidate = candidateRepository
                .findByEmail(candidateEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate not found"));

        // Get job
        JobEntity job = jobRepository.findById(dto.getJobId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job", dto.getJobId()));

        // Check if job is active
        if (!job.isActive()) {
            throw new RuntimeException(
                    "This job is no longer active");
        }

        // Check if already applied
        boolean alreadyApplied = applicationRepository
                .existsByCandidateIdAndJobId(
                        candidate.getId(), job.getId());

        if (alreadyApplied) {
            throw new RuntimeException(
                    "You have already applied for this job");
        }

        // Create application
        JobApplicationEntity application =
                new JobApplicationEntity();
        application.setCandidate(candidate);
        application.setJob(job);
        application.setStatus("APPLIED");
        application.setCoverLetter(dto.getCoverLetter());
        application.setResumePath(dto.getResumePath() != null
                ? dto.getResumePath()
                : candidate.getResumeLink());

        JobApplicationEntity saved =
                applicationRepository.save(application);
        return mapToResponse(saved);
    }

    //  Candidate views own applications
    @Override
    public List<JobApplicationResponseDto> getMyApplications(
            String candidateEmail) {

        CandidateEntity candidate = candidateRepository
                .findByEmail(candidateEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate not found"));

        return applicationRepository
                .findByCandidateId(candidate.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Candidate withdraws application
    @Override
    public void withdrawApplication(
            String candidateEmail, Long applicationId) {

        CandidateEntity candidate = candidateRepository
                .findByEmail(candidateEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate not found"));

        JobApplicationEntity application = applicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Application", applicationId));

        // Make sure candidate owns this application
        if (!application.getCandidate().getId()
                .equals(candidate.getId())) {
            throw new UnauthorizedException(
                    "You are not authorized to withdraw this application");
        }

        // Only allow withdraw if status is APPLIED
        if (!application.getStatus().equals("APPLIED")) {
            throw new RuntimeException(
                    "Cannot withdraw application at this stage");
        }

        applicationRepository.delete(application);
    }

    // Admin - Get all applications
    @Override
    public List<JobApplicationResponseDto> getAllApplications() {
        return applicationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Admin - Get applications by job
    @Override
    public List<JobApplicationResponseDto> getApplicationsByJob(
            Long jobId) {
        return applicationRepository.findByJobId(jobId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Admin - Get applications by status
    @Override
    public List<JobApplicationResponseDto> getApplicationsByStatus(
            String status) {
        return applicationRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Admin - Update application status
    @Override
    public JobApplicationResponseDto updateApplicationStatus(
            Long applicationId, String status) {

        JobApplicationEntity application = applicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Application", applicationId));

        // Validate status value
        List<String> validStatuses = List.of(
                "APPLIED",
                "SHORTLISTED",
                "INTERVIEW",
                "SELECTED",
                "REJECTED"
        );

        if (!validStatuses.contains(status.toUpperCase())) {
            throw new RuntimeException(
                    "Invalid status. Valid values: "
                            + validStatuses);
        }

        application.setStatus(status.toUpperCase());
        JobApplicationEntity updated =
                applicationRepository.save(application);
        return mapToResponse(updated);
    }

    //  Get application by ID
    @Override
    public JobApplicationResponseDto getApplicationById(Long id) {
        JobApplicationEntity application = applicationRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Application", id));
        return mapToResponse(application);
    }

    //  Count applications per job
    @Override
    public long countApplicationsByJob(Long jobId) {
        return applicationRepository.countByJobId(jobId);
    }

    // ── Helper Method ──────────────────────────────

    private JobApplicationResponseDto mapToResponse(
            JobApplicationEntity application) {

        JobApplicationResponseDto dto =
                new JobApplicationResponseDto();

        dto.setId(application.getId());

        // Candidate info
        dto.setCandidateId(application.getCandidate().getId());
        dto.setCandidateName(application.getCandidate().getName());
        dto.setCandidateEmail(application.getCandidate().getEmail());
        dto.setCandidatePhone(application.getCandidate().getMobile());

        // Job info
        dto.setJobId(application.getJob().getId());
        dto.setJobTitle(application.getJob().getTitle());
        dto.setCompanyName(application.getJob().getCompanyName());
        dto.setJobLocation(application.getJob().getLocation());

        // Application info
        dto.setStatus(application.getStatus());
        dto.setCoverLetter(application.getCoverLetter());
        dto.setResumePath(application.getResumePath());
        dto.setAppliedAt(application.getAppliedAt());
        dto.setUpdatedAt(application.getUpdatedAt());

        return dto;
    }
}