package com.vibrantminds.vibrantminds.job;

import com.vibrantminds.vibrantminds.exception.ResourceNotFoundException;
import com.vibrantminds.vibrantminds.job.dto.JobRequestDto;
import com.vibrantminds.vibrantminds.job.dto.JobResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    //  Create Job (Admin)
    @Override
    public JobResponseDto createJob(JobRequestDto dto) {
        JobEntity job = new JobEntity();
        mapToEntity(dto, job);
        JobEntity saved = jobRepository.save(job);
        return mapToResponse(saved);
    }

    //  Update Job (Admin)
    @Override
    public JobResponseDto updateJob(Long id, JobRequestDto dto) {
        JobEntity job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", id));
        mapToEntity(dto, job);
        JobEntity updated = jobRepository.save(job);
        return mapToResponse(updated);
    }

    //  Delete Job (Admin)
    @Override
    public void deleteJob(Long id) {
        JobEntity job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", id));
        jobRepository.delete(job);
    }

    //  Toggle Active/Inactive (Admin)
    @Override
    public JobResponseDto toggleJobStatus(Long id) {
        JobEntity job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", id));
        job.setActive(!job.isActive());
        JobEntity updated = jobRepository.save(job);
        return mapToResponse(updated);
    }

    //  Get All Active Jobs (Public)
    @Override
    public List<JobResponseDto> getAllActiveJobs() {
        return jobRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get All Jobs (Admin)
    @Override
    public List<JobResponseDto> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get Job By ID (Public)
    @Override
    public JobResponseDto getJobById(Long id) {
        JobEntity job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", id));
        return mapToResponse(job);
    }

    //  Search by Title (Public)
    @Override
    public List<JobResponseDto> searchByTitle(String title) {
        return jobRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Search by Location (Public)
    @Override
    public List<JobResponseDto> searchByLocation(String location) {
        return jobRepository.findByLocationContainingIgnoreCase(location)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Search by Job Type (Public)
    @Override
    public List<JobResponseDto> searchByJobType(String jobType) {
        return jobRepository.findByJobType(jobType)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ── Helper Methods ──────────────────────────────

    private void mapToEntity(JobRequestDto dto, JobEntity job) {
        job.setTitle(dto.getTitle());
        job.setCompanyName(dto.getCompanyName());
        job.setLocation(dto.getLocation());
        job.setJobType(dto.getJobType());
        job.setExperience(dto.getExperience());
        job.setSalary(dto.getSalary());
        job.setDescription(dto.getDescription());
        job.setSkills(dto.getSkills());
        job.setQualification(dto.getQualification());
        job.setLastDateToApply(dto.getLastDateToApply());
        job.setActive(dto.isActive());
    }

    private JobResponseDto mapToResponse(JobEntity job) {
        JobResponseDto dto = new JobResponseDto();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setCompanyName(job.getCompanyName());
        dto.setLocation(job.getLocation());
        dto.setJobType(job.getJobType());
        dto.setExperience(job.getExperience());
        dto.setSalary(job.getSalary());
        dto.setDescription(job.getDescription());
        dto.setSkills(job.getSkills());
        dto.setQualification(job.getQualification());
        dto.setLastDateToApply(job.getLastDateToApply());
        dto.setActive(job.isActive());
        dto.setCreatedAt(job.getCreatedAt());
        dto.setUpdatedAt(job.getUpdatedAt());
        return dto;
    }
}