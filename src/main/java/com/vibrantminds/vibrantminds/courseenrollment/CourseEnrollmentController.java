package com.vibrantminds.vibrantminds.courseenrollment;

import com.vibrantminds.vibrantminds.courseenrollment.dto.CourseEnrollmentRequestDto;
import com.vibrantminds.vibrantminds.courseenrollment.dto.CourseEnrollmentResponseDto;
import com.vibrantminds.vibrantminds.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class CourseEnrollmentController {

    private final CourseEnrollmentService enrollmentService;

    //  CANDIDATE - Enroll in course
    // POST /api/enrollments/enroll
    @PostMapping("/enroll")
    public ResponseEntity<ApiResponse<CourseEnrollmentResponseDto>>
            enrollInCourse(
                @Valid @RequestBody CourseEnrollmentRequestDto dto,
                @AuthenticationPrincipal UserDetails userDetails) {

        CourseEnrollmentResponseDto enrollment =
                enrollmentService.enrollInCourse(
                        userDetails.getUsername(), dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Enrolled successfully", enrollment));
    }

    //  CANDIDATE - View my enrolled courses
    // GET /api/enrollments/my
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<CourseEnrollmentResponseDto>>>
            getMyCourses(
                @AuthenticationPrincipal UserDetails userDetails) {

        List<CourseEnrollmentResponseDto> enrollments =
                enrollmentService.getMyCourses(
                        userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Enrollments fetched successfully",
                        enrollments));
    }

    //  CANDIDATE - Cancel enrollment
    // PATCH /api/enrollments/cancel/{enrollmentId}
    @PatchMapping("/cancel/{enrollmentId}")
    public ResponseEntity<ApiResponse<?>> cancelEnrollment(
            @PathVariable Long enrollmentId,
            @AuthenticationPrincipal UserDetails userDetails) {

        enrollmentService.cancelEnrollment(
                userDetails.getUsername(), enrollmentId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Enrollment cancelled successfully"));
    }

    //  ADMIN - Get all enrollments
    // GET /api/enrollments/admin/all
    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponse<List<CourseEnrollmentResponseDto>>>
            getAllEnrollments() {

        List<CourseEnrollmentResponseDto> enrollments =
                enrollmentService.getAllEnrollments();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "All enrollments fetched successfully",
                        enrollments));
    }

    //  ADMIN - Get enrollments by course
    // GET /api/enrollments/admin/course/{courseId}
    @GetMapping("/admin/course/{courseId}")
    public ResponseEntity<ApiResponse<List<CourseEnrollmentResponseDto>>>
            getEnrollmentsByCourse(@PathVariable Long courseId) {

        List<CourseEnrollmentResponseDto> enrollments =
                enrollmentService.getEnrollmentsByCourse(courseId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Enrollments fetched successfully",
                        enrollments));
    }

    //  ADMIN - Get enrollments by status
    // GET /api/enrollments/admin/status?status=ENROLLED
    @GetMapping("/admin/status")
    public ResponseEntity<ApiResponse<List<CourseEnrollmentResponseDto>>>
            getEnrollmentsByStatus(@RequestParam String status) {

        List<CourseEnrollmentResponseDto> enrollments =
                enrollmentService.getEnrollmentsByStatus(status);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Enrollments fetched successfully",
                        enrollments));
    }

    //  ADMIN - Get enrollments by payment status
    // GET /api/enrollments/admin/payment?status=PENDING
    @GetMapping("/admin/payment")
    public ResponseEntity<ApiResponse<List<CourseEnrollmentResponseDto>>>
            getEnrollmentsByPaymentStatus(
                @RequestParam String status) {

        List<CourseEnrollmentResponseDto> enrollments =
                enrollmentService.getEnrollmentsByPaymentStatus(
                        status);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Enrollments fetched successfully",
                        enrollments));
    }

    // ADMIN - Update enrollment status
    // PATCH /api/enrollments/admin/{id}/status?status=COMPLETED
    @PatchMapping("/admin/{id}/status")
    public ResponseEntity<ApiResponse<CourseEnrollmentResponseDto>>
            updateEnrollmentStatus(
                @PathVariable Long id,
                @RequestParam String status) {

        CourseEnrollmentResponseDto enrollment =
                enrollmentService.updateEnrollmentStatus(id, status);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Status updated successfully", enrollment));
    }

    //  ADMIN - Update payment status
    // PATCH /api/enrollments/admin/{id}/payment?status=PAID
    @PatchMapping("/admin/{id}/payment")
    public ResponseEntity<ApiResponse<CourseEnrollmentResponseDto>>
            updatePaymentStatus(
                @PathVariable Long id,
                @RequestParam String status) {

        CourseEnrollmentResponseDto enrollment =
                enrollmentService.updatePaymentStatus(id, status);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment status updated successfully",
                        enrollment));
    }

    //  ADMIN - Get enrollment by ID
    // GET /api/enrollments/admin/{id}
    @GetMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<CourseEnrollmentResponseDto>>
            getEnrollmentById(@PathVariable Long id) {

        CourseEnrollmentResponseDto enrollment =
                enrollmentService.getEnrollmentById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Enrollment fetched successfully",
                        enrollment));
    }

    //  ADMIN - Count enrollments per course
    // GET /api/enrollments/admin/count/{courseId}
    @GetMapping("/admin/count/{courseId}")
    public ResponseEntity<ApiResponse<Long>> countByCourse(
            @PathVariable Long courseId) {

        long count = enrollmentService
                .countEnrollmentsByCourse(courseId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Count fetched successfully", count));
    }
}