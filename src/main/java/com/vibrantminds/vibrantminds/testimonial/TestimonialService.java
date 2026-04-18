package com.vibrantminds.vibrantminds.testimonial;

import com.vibrantminds.vibrantminds.testimonial.dto.TestimonialRequestDto;
import com.vibrantminds.vibrantminds.testimonial.dto.TestimonialResponseDto;
import java.util.List;

public interface TestimonialService {

    // Admin operations
    TestimonialResponseDto createTestimonial(
            TestimonialRequestDto dto);
    TestimonialResponseDto updateTestimonial(
            Long id, TestimonialRequestDto dto);
    void deleteTestimonial(Long id);
    TestimonialResponseDto toggleActive(Long id);
    TestimonialResponseDto toggleFeatured(Long id);

    // Public operations
    List<TestimonialResponseDto> getAllActiveTestimonials();
    List<TestimonialResponseDto> getFeaturedTestimonials();
    TestimonialResponseDto getTestimonialById(Long id);

    // Admin operations
    List<TestimonialResponseDto> getAllTestimonials();
    List<TestimonialResponseDto> getByRating(int rating);
    List<TestimonialResponseDto> getByCompany(String companyName);
    List<TestimonialResponseDto> getByCourse(String course);
    List<TestimonialResponseDto> getByCandidate(Long candidateId);
}