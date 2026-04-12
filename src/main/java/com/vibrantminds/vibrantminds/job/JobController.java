package com.vibrantminds.vibrantminds.job;

import com.vibrantminds.vibrantminds.exception.ApiResponse;
import com.vibrantminds.vibrantminds.job.dto.JobRequestDto;
import com.vibrantminds.vibrantminds.job.dto.JobResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    //  PUBLIC - Get all active jobs
    // GET /api/jobs
    @GetMapping
    public ResponseEntity<ApiResponse<List<JobResponseDto>>> getAllActiveJobs() {
        List<JobResponseDto> jobs = jobService.getAllActiveJobs();
        return ResponseEntity.ok(
                ApiResponse.success("Jobs fetched successfully", jobs));
    }

    //  PUBLIC - Get job by id
    // GET /api/jobs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobResponseDto>> getJobById(
            @PathVariable Long id) {
        JobResponseDto job = jobService.getJobById(id);
        return ResponseEntity.ok(
                ApiResponse.success("Job fetched successfully", job));
    }

    //  PUBLIC - Search jobs
    // GET /api/jobs/search?title=java
    // GET /api/jobs/search?location=pune
    // GET /api/jobs/search?jobType=Internship
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<JobResponseDto>>> searchJobs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType) {

        List<JobResponseDto> jobs;

        if (title != null) {
            jobs = jobService.searchByTitle(title);
        } else if (location != null) {
            jobs = jobService.searchByLocation(location);
        } else if (jobType != null) {
            jobs = jobService.searchByJobType(jobType);
        } else {
            jobs = jobService.getAllActiveJobs();
        }

        return ResponseEntity.ok(
                ApiResponse.success("Jobs fetched successfully", jobs));
    }

    //  ADMIN - Get all jobs including inactive
    // GET /api/jobs/admin/all
    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponse<List<JobResponseDto>>> getAllJobs() {
        List<JobResponseDto> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(
                ApiResponse.success("All jobs fetched successfully", jobs));
    }

    //  ADMIN - Create job
    // POST /api/jobs/admin
    @PostMapping("/admin")
    public ResponseEntity<ApiResponse<JobResponseDto>> createJob(
            @Valid @RequestBody JobRequestDto dto) {
        JobResponseDto job = jobService.createJob(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Job created successfully", job));
    }

    //  ADMIN - Update job
    // PUT /api/jobs/admin/{id}
    @PutMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<JobResponseDto>> updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequestDto dto) {
        JobResponseDto job = jobService.updateJob(id, dto);
        return ResponseEntity.ok(
                ApiResponse.success("Job updated successfully", job));
    }

    //  ADMIN - Toggle job active/inactive
    // PATCH /api/jobs/admin/{id}/toggle
    @PatchMapping("/admin/{id}/toggle")
    public ResponseEntity<ApiResponse<JobResponseDto>> toggleJobStatus(
            @PathVariable Long id) {
        JobResponseDto job = jobService.toggleJobStatus(id);
        return ResponseEntity.ok(
                ApiResponse.success("Job status updated successfully", job));
    }

    //  ADMIN - Delete job
    // DELETE /api/jobs/admin/{id}
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<?>> deleteJob(
            @PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok(
                ApiResponse.success("Job deleted successfully"));
    }
}