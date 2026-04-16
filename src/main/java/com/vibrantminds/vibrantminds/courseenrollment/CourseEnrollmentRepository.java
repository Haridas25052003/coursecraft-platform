package com.vibrantminds.vibrantminds.courseenrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseEnrollmentRepository
        extends JpaRepository<CourseEnrollmentEntity, Long> {

    // Get all enrollments by candidate
    List<CourseEnrollmentEntity> findByCandidateId(
            Long candidateId);

    // Get all enrollments for a course
    List<CourseEnrollmentEntity> findByCourseId(Long courseId);

    // Get all enrollments by status
    List<CourseEnrollmentEntity> findByStatus(String status);

    // Get all enrollments by payment status
    List<CourseEnrollmentEntity> findByPaymentStatus(
            String paymentStatus);

    // Check if candidate already enrolled
    boolean existsByCandidateIdAndCourseId(
            Long candidateId, Long courseId);

    // Get specific enrollment
    Optional<CourseEnrollmentEntity> findByCandidateIdAndCourseId(
            Long candidateId, Long courseId);

    // Count enrollments per course
    long countByCourseId(Long courseId);
}