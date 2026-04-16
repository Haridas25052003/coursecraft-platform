package com.vibrantminds.vibrantminds.courseenrollment;

import com.vibrantminds.vibrantminds.candidate.CandidateEntity;
import com.vibrantminds.vibrantminds.candidate.CandidateRepository;
import com.vibrantminds.vibrantminds.course.CourseEntity;
import com.vibrantminds.vibrantminds.course.CourseRepository;
import com.vibrantminds.vibrantminds.courseenrollment.dto.CourseEnrollmentRequestDto;
import com.vibrantminds.vibrantminds.courseenrollment.dto.CourseEnrollmentResponseDto;
import com.vibrantminds.vibrantminds.exception.ResourceNotFoundException;
import com.vibrantminds.vibrantminds.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseEnrollmentServiceImpl
        implements CourseEnrollmentService {

    private final CourseEnrollmentRepository enrollmentRepository;
    private final CandidateRepository candidateRepository;
    private final CourseRepository courseRepository;

    //  Candidate enrolls in course
    @Override
    public CourseEnrollmentResponseDto enrollInCourse(
            String candidateEmail,
            CourseEnrollmentRequestDto dto) {

        // Get candidate
        CandidateEntity candidate = candidateRepository
                .findByEmail(candidateEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate not found"));

        // Get course
        CourseEntity course = courseRepository
                .findById(dto.getCourseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course", dto.getCourseId()));

        // Check if course is active
        if (!course.isActive()) {
            throw new RuntimeException(
                    "This course is no longer active");
        }

        // Check if already enrolled
        boolean alreadyEnrolled = enrollmentRepository
                .existsByCandidateIdAndCourseId(
                        candidate.getId(), course.getId());

        if (alreadyEnrolled) {
            throw new RuntimeException(
                    "You are already enrolled in this course");
        }

        // Create enrollment
        CourseEnrollmentEntity enrollment =
                new CourseEnrollmentEntity();
        enrollment.setCandidate(candidate);
        enrollment.setCourse(course);
        enrollment.setStatus("ENROLLED");
        enrollment.setPaymentStatus("PENDING");
        enrollment.setRemarks(dto.getRemarks());

        CourseEnrollmentEntity saved =
                enrollmentRepository.save(enrollment);
        return mapToResponse(saved);
    }

    //  Candidate views own enrolled courses
    @Override
    public List<CourseEnrollmentResponseDto> getMyCourses(
            String candidateEmail) {

        CandidateEntity candidate = candidateRepository
                .findByEmail(candidateEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate not found"));

        return enrollmentRepository
                .findByCandidateId(candidate.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Candidate cancels enrollment
    @Override
    public void cancelEnrollment(
            String candidateEmail, Long enrollmentId) {

        CandidateEntity candidate = candidateRepository
                .findByEmail(candidateEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate not found"));

        CourseEnrollmentEntity enrollment = enrollmentRepository
                .findById(enrollmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enrollment", enrollmentId));

        // Check candidate owns this enrollment
        if (!enrollment.getCandidate().getId()
                .equals(candidate.getId())) {
            throw new UnauthorizedException(
                    "You are not authorized to cancel " +
                    "this enrollment");
        }

        // Only allow cancel if status is ENROLLED
        if (!enrollment.getStatus().equals("ENROLLED")) {
            throw new RuntimeException(
                    "Cannot cancel enrollment at this stage");
        }

        enrollment.setStatus("CANCELLED");
        enrollmentRepository.save(enrollment);
    }

    //  Admin - Get all enrollments
    @Override
    public List<CourseEnrollmentResponseDto> getAllEnrollments() {
        return enrollmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Admin - Get enrollments by course
    @Override
    public List<CourseEnrollmentResponseDto> getEnrollmentsByCourse(
            Long courseId) {
        return enrollmentRepository.findByCourseId(courseId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Admin - Get enrollments by status
    @Override
    public List<CourseEnrollmentResponseDto> getEnrollmentsByStatus(
            String status) {
        return enrollmentRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Admin - Get enrollments by payment status
    @Override
    public List<CourseEnrollmentResponseDto>
            getEnrollmentsByPaymentStatus(String paymentStatus) {
        return enrollmentRepository
                .findByPaymentStatus(paymentStatus)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Admin - Update enrollment status
    @Override
    public CourseEnrollmentResponseDto updateEnrollmentStatus(
            Long enrollmentId, String status) {

        CourseEnrollmentEntity enrollment = enrollmentRepository
                .findById(enrollmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enrollment", enrollmentId));

        List<String> validStatuses = List.of(
                "ENROLLED",
                "IN_PROGRESS",
                "COMPLETED",
                "CANCELLED"
        );

        if (!validStatuses.contains(status.toUpperCase())) {
            throw new RuntimeException(
                    "Invalid status. Valid values : "
                            + validStatuses);
        }

        enrollment.setStatus(status.toUpperCase());

        // Set completed date if status is COMPLETED
        if (status.equalsIgnoreCase("COMPLETED")) {
            enrollment.setCompletedAt(LocalDateTime.now());
        }

        CourseEnrollmentEntity updated =
                enrollmentRepository.save(enrollment);
        return mapToResponse(updated);
    }

    //  Admin - Update payment status
    @Override
    public CourseEnrollmentResponseDto updatePaymentStatus(
            Long enrollmentId, String paymentStatus) {

        CourseEnrollmentEntity enrollment = enrollmentRepository
                .findById(enrollmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enrollment", enrollmentId));

        List<String> validStatuses = List.of(
                "PENDING",
                "PAID",
                "FREE"
        );

        if (!validStatuses.contains(paymentStatus.toUpperCase())) {
            throw new RuntimeException(
                    "Invalid payment status. Valid values : "
                            + validStatuses);
        }

        enrollment.setPaymentStatus(paymentStatus.toUpperCase());
        CourseEnrollmentEntity updated =
                enrollmentRepository.save(enrollment);
        return mapToResponse(updated);
    }

    //  Get enrollment by ID
    @Override
    public CourseEnrollmentResponseDto getEnrollmentById(Long id) {
        CourseEnrollmentEntity enrollment = enrollmentRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enrollment", id));
        return mapToResponse(enrollment);
    }

    //  Count enrollments per course
    @Override
    public long countEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.countByCourseId(courseId);
    }

    // ── Helper Method ──────────────────────────────

    private CourseEnrollmentResponseDto mapToResponse(
            CourseEnrollmentEntity enrollment) {

        CourseEnrollmentResponseDto dto =
                new CourseEnrollmentResponseDto();

        dto.setId(enrollment.getId());

        // Candidate info
        dto.setCandidateId(enrollment.getCandidate().getId());
        dto.setCandidateName(enrollment.getCandidate().getName());
        dto.setCandidateEmail(enrollment.getCandidate().getEmail());
        dto.setCandidatePhone(enrollment.getCandidate().getMobile());

        // Course info
        dto.setCourseId(enrollment.getCourse().getId());
        dto.setCourseTitle(enrollment.getCourse().getTitle());
        dto.setCourseDuration(enrollment.getCourse().getDuration());
        dto.setCourseMode(enrollment.getCourse().getMode());
        dto.setCourseFees(enrollment.getCourse().getFees());

        // Enrollment info
        dto.setStatus(enrollment.getStatus());
        dto.setPaymentStatus(enrollment.getPaymentStatus());
        dto.setRemarks(enrollment.getRemarks());
        dto.setEnrolledAt(enrollment.getEnrolledAt());
        dto.setCompletedAt(enrollment.getCompletedAt());
        dto.setUpdatedAt(enrollment.getUpdatedAt());

        return dto;
    }
}