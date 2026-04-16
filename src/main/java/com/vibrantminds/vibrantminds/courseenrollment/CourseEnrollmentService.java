package com.vibrantminds.vibrantminds.courseenrollment;

import com.vibrantminds.vibrantminds.courseenrollment.dto.CourseEnrollmentRequestDto;
import com.vibrantminds.vibrantminds.courseenrollment.dto.CourseEnrollmentResponseDto;
import java.util.List;

public interface CourseEnrollmentService {

    // Candidate operations
    CourseEnrollmentResponseDto enrollInCourse(
            String candidateEmail,
            CourseEnrollmentRequestDto dto);

    List<CourseEnrollmentResponseDto> getMyCourses(
            String candidateEmail);

    void cancelEnrollment(
            String candidateEmail, Long enrollmentId);

    // Admin operations
    List<CourseEnrollmentResponseDto> getAllEnrollments();

    List<CourseEnrollmentResponseDto> getEnrollmentsByCourse(
            Long courseId);

    List<CourseEnrollmentResponseDto> getEnrollmentsByStatus(
            String status);

    List<CourseEnrollmentResponseDto> getEnrollmentsByPaymentStatus(
            String paymentStatus);

    CourseEnrollmentResponseDto updateEnrollmentStatus(
            Long enrollmentId, String status);

    CourseEnrollmentResponseDto updatePaymentStatus(
            Long enrollmentId, String paymentStatus);

    CourseEnrollmentResponseDto getEnrollmentById(Long id);

    long countEnrollmentsByCourse(Long courseId);
}