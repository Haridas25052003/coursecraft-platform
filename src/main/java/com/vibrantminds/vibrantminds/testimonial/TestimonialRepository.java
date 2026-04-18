package com.vibrantminds.vibrantminds.testimonial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TestimonialRepository
        extends JpaRepository<TestimonialEntity, Long> {

    // Get all active testimonials
    List<TestimonialEntity> findByActiveTrue();

    // Get featured testimonials (for homepage)
    List<TestimonialEntity> findByFeaturedTrueAndActiveTrue();

    // Get by candidate
    List<TestimonialEntity> findByCandidateId(Long candidateId);

    // Get by rating
    List<TestimonialEntity> findByRating(int rating);

    // Get by company
    List<TestimonialEntity> findByCompanyNameContainingIgnoreCase(
            String companyName);

    // Get by course
    List<TestimonialEntity> findByCourseContainingIgnoreCase(
            String course);
}