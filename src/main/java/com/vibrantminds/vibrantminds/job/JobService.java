package com.vibrantminds.vibrantminds.job;

import com.vibrantminds.vibrantminds.job.dto.JobRequestDto;
import com.vibrantminds.vibrantminds.job.dto.JobResponseDto;
import java.util.List;

public interface JobService {

    // Admin operations
    JobResponseDto createJob(JobRequestDto dto);
    JobResponseDto updateJob(Long id, JobRequestDto dto);
    void deleteJob(Long id);
    JobResponseDto toggleJobStatus(Long id);

    // Public operations
    List<JobResponseDto> getAllActiveJobs();
    List<JobResponseDto> getAllJobs();
    JobResponseDto getJobById(Long id);

    // Search operations
    List<JobResponseDto> searchByTitle(String title);
    List<JobResponseDto> searchByLocation(String location);
    List<JobResponseDto> searchByJobType(String jobType);
}