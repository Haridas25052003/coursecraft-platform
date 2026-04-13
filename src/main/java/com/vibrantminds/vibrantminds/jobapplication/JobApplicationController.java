package com.vibrantminds.vibrantminds.jobapplication;

import com.vibrantminds.vibrantminds.exception.ApiResponse;
import com.vibrantminds.vibrantminds.jobapplication.dto.JobApplicationRequestDto;
import com.vibrantminds.vibrantminds.jobapplication.dto.JobApplicationResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService applicationService;

    //  CANDIDATE - Apply for job
    // POST /api/applications/apply
    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<JobApplicationResponseDto>>
            applyForJob(
                @Valid @RequestBody JobApplicationRequestDto dto,
                @AuthenticationPrincipal UserDetails userDetails) {

        JobApplicationResponseDto application =
                applicationService.applyForJob(
                        userDetails.getUsername(), dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Applied successfully", application));
    }

    //  CANDIDATE - View my applications
    // GET /api/applications/my
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<JobApplicationResponseDto>>>
            getMyApplications(
                @AuthenticationPrincipal UserDetails userDetails) {

        List<JobApplicationResponseDto> applications =
                applicationService.getMyApplications(
                        userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Applications fetched successfully",
                        applications));
    }

    //  CANDIDATE - Withdraw application
    // DELETE /api/applications/withdraw/{applicationId}
    @DeleteMapping("/withdraw/{applicationId}")
    public ResponseEntity<ApiResponse<?>> withdrawApplication(
            @PathVariable Long applicationId,
            @AuthenticationPrincipal UserDetails userDetails) {

        applicationService.withdrawApplication(
                userDetails.getUsername(), applicationId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Application withdrawn successfully"));
    }

    //  ADMIN - Get all applications
    // GET /api/applications/admin/all
    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponse<List<JobApplicationResponseDto>>>
            getAllApplications() {

        List<JobApplicationResponseDto> applications =
                applicationService.getAllApplications();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "All applications fetched successfully",
                        applications));
    }

    //  ADMIN - Get applications by job
    // GET /api/applications/admin/job/{jobId}
    @GetMapping("/admin/job/{jobId}")
    public ResponseEntity<ApiResponse<List<JobApplicationResponseDto>>>
            getApplicationsByJob(@PathVariable Long jobId) {

        List<JobApplicationResponseDto> applications =
                applicationService.getApplicationsByJob(jobId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Applications fetched successfully",
                        applications));
    }

    //  ADMIN - Get applications by status
    // GET /api/applications/admin/status?status=APPLIED
    @GetMapping("/admin/status")
    public ResponseEntity<ApiResponse<List<JobApplicationResponseDto>>>
            getApplicationsByStatus(
                @RequestParam String status) {

        List<JobApplicationResponseDto> applications =
                applicationService.getApplicationsByStatus(status);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Applications fetched successfully",
                        applications));
    }

    //  ADMIN - Update application status
    // PATCH /api/applications/admin/{id}/status?status=SHORTLISTED
    @PatchMapping("/admin/{id}/status")
    public ResponseEntity<ApiResponse<JobApplicationResponseDto>>
            updateStatus(
                @PathVariable Long id,
                @RequestParam String status) {

        JobApplicationResponseDto application =
                applicationService.updateApplicationStatus(id, status);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Status updated successfully", application));
    }

    //  ADMIN - Get application by ID
    // GET /api/applications/admin/{id}
    @GetMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<JobApplicationResponseDto>>
            getApplicationById(@PathVariable Long id) {

        JobApplicationResponseDto application =
                applicationService.getApplicationById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Application fetched successfully",
                        application));
    }

    //  ADMIN - Count applications per job
    // GET /api/applications/admin/count/{jobId}
    @GetMapping("/admin/count/{jobId}")
    public ResponseEntity<ApiResponse<Long>> countByJob(
            @PathVariable Long jobId) {

        long count = applicationService.countApplicationsByJob(jobId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Count fetched successfully", count));
    }
}