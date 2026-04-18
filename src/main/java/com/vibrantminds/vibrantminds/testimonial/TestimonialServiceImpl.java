package com.vibrantminds.vibrantminds.testimonial;

import com.vibrantminds.vibrantminds.candidate.CandidateEntity;
import com.vibrantminds.vibrantminds.candidate.CandidateRepository;
import com.vibrantminds.vibrantminds.exception.ResourceNotFoundException;
import com.vibrantminds.vibrantminds.testimonial.dto.TestimonialRequestDto;
import com.vibrantminds.vibrantminds.testimonial.dto.TestimonialResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestimonialServiceImpl implements TestimonialService {

    private final TestimonialRepository testimonialRepository;
    private final CandidateRepository candidateRepository;

    //  Create Testimonial (Admin)
    @Override
    public TestimonialResponseDto createTestimonial(
            TestimonialRequestDto dto) {

        TestimonialEntity testimonial = new TestimonialEntity();

        // Link to candidate if candidateId provided
        if (dto.getCandidateId() != null) {
            CandidateEntity candidate = candidateRepository
                    .findById(dto.getCandidateId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Candidate",
                                    dto.getCandidateId()));
            testimonial.setCandidate(candidate);

            // Auto fill name and photo from candidate
            // if not provided manually
            if (dto.getPersonName() == null) {
                testimonial.setPersonName(candidate.getName());
            }
            if (dto.getPersonPhoto() == null) {
                testimonial.setPersonPhoto(
                        candidate.getGithubProfile());
            }
        }

        mapToEntity(dto, testimonial);
        TestimonialEntity saved =
                testimonialRepository.save(testimonial);
        return mapToResponse(saved);
    }

    //  Update Testimonial (Admin)
    @Override
    public TestimonialResponseDto updateTestimonial(
            Long id, TestimonialRequestDto dto) {

        TestimonialEntity testimonial = testimonialRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Testimonial", id));

        if (dto.getCandidateId() != null) {
            CandidateEntity candidate = candidateRepository
                    .findById(dto.getCandidateId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Candidate",
                                    dto.getCandidateId()));
            testimonial.setCandidate(candidate);
        }

        mapToEntity(dto, testimonial);
        TestimonialEntity updated =
                testimonialRepository.save(testimonial);
        return mapToResponse(updated);
    }

    //  Delete Testimonial (Admin)
    @Override
    public void deleteTestimonial(Long id) {
        TestimonialEntity testimonial = testimonialRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Testimonial", id));
        testimonialRepository.delete(testimonial);
    }

    //  Toggle Active (Admin)
    @Override
    public TestimonialResponseDto toggleActive(Long id) {
        TestimonialEntity testimonial = testimonialRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Testimonial", id));
        testimonial.setActive(!testimonial.isActive());
        TestimonialEntity updated =
                testimonialRepository.save(testimonial);
        return mapToResponse(updated);
    }

    //  Toggle Featured (Admin)
    @Override
    public TestimonialResponseDto toggleFeatured(Long id) {
        TestimonialEntity testimonial = testimonialRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Testimonial", id));
        testimonial.setFeatured(!testimonial.isFeatured());
        TestimonialEntity updated =
                testimonialRepository.save(testimonial);
        return mapToResponse(updated);
    }

    //  Get All Active Testimonials (Public)
    @Override
    public List<TestimonialResponseDto> getAllActiveTestimonials() {
        return testimonialRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get Featured Testimonials (Public - Homepage)
    @Override
    public List<TestimonialResponseDto> getFeaturedTestimonials() {
        return testimonialRepository
                .findByFeaturedTrueAndActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get Testimonial By ID
    @Override
    public TestimonialResponseDto getTestimonialById(Long id) {
        TestimonialEntity testimonial = testimonialRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Testimonial", id));
        return mapToResponse(testimonial);
    }

    //  Get All Testimonials (Admin)
    @Override
    public List<TestimonialResponseDto> getAllTestimonials() {
        return testimonialRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get By Rating
    @Override
    public List<TestimonialResponseDto> getByRating(int rating) {
        return testimonialRepository.findByRating(rating)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get By Company
    @Override
    public List<TestimonialResponseDto> getByCompany(
            String companyName) {
        return testimonialRepository
                .findByCompanyNameContainingIgnoreCase(companyName)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get By Course
    @Override
    public List<TestimonialResponseDto> getByCourse(String course) {
        return testimonialRepository
                .findByCourseContainingIgnoreCase(course)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get By Candidate
    @Override
    public List<TestimonialResponseDto> getByCandidate(
            Long candidateId) {
        return testimonialRepository.findByCandidateId(candidateId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ── Helper Methods ──────────────────────────────

    private void mapToEntity(
            TestimonialRequestDto dto,
            TestimonialEntity testimonial) {
        testimonial.setPersonName(dto.getPersonName());
        testimonial.setPersonPhoto(dto.getPersonPhoto());
        testimonial.setPersonDesignation(dto.getPersonDesignation());
        testimonial.setCompanyName(dto.getCompanyName());
        testimonial.setMessage(dto.getMessage());
        testimonial.setRating(dto.getRating());
        testimonial.setCourse(dto.getCourse());
        testimonial.setActive(dto.isActive());
        testimonial.setFeatured(dto.isFeatured());
    }

    private TestimonialResponseDto mapToResponse(
            TestimonialEntity testimonial) {

        TestimonialResponseDto dto = new TestimonialResponseDto();
        dto.setId(testimonial.getId());

        // Candidate info if linked
        if (testimonial.getCandidate() != null) {
            dto.setCandidateId(testimonial.getCandidate().getId());
        }

        dto.setPersonName(testimonial.getPersonName());
        dto.setPersonPhoto(testimonial.getPersonPhoto());
        dto.setPersonDesignation(testimonial.getPersonDesignation());
        dto.setCompanyName(testimonial.getCompanyName());
        dto.setMessage(testimonial.getMessage());
        dto.setRating(testimonial.getRating());
        dto.setCourse(testimonial.getCourse());
        dto.setActive(testimonial.isActive());
        dto.setFeatured(testimonial.isFeatured());
        dto.setCreatedAt(testimonial.getCreatedAt());
        dto.setUpdatedAt(testimonial.getUpdatedAt());

        return dto;
    }
}