package com.vibrantminds.vibrantminds.testimonial;

import com.vibrantminds.vibrantminds.exception.ApiResponse;
import com.vibrantminds.vibrantminds.testimonial.dto.TestimonialRequestDto;
import com.vibrantminds.vibrantminds.testimonial.dto.TestimonialResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/testimonials")
@RequiredArgsConstructor
public class TestimonialController {

    private final TestimonialService testimonialService;

    //  PUBLIC - Get all active testimonials
    // GET /api/testimonials
    @GetMapping
    public ResponseEntity<ApiResponse<List<TestimonialResponseDto>>>
            getAllActiveTestimonials() {
        List<TestimonialResponseDto> testimonials =
                testimonialService.getAllActiveTestimonials();
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Testimonials fetched successfully",
                        testimonials));
    }

    //  PUBLIC - Get featured testimonials (for homepage)
    // GET /api/testimonials/featured
    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<List<TestimonialResponseDto>>>
            getFeaturedTestimonials() {
        List<TestimonialResponseDto> testimonials =
                testimonialService.getFeaturedTestimonials();
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Featured testimonials fetched successfully",
                        testimonials));
    }

    //  PUBLIC - Get testimonial by id
    // GET /api/testimonials/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TestimonialResponseDto>>
            getTestimonialById(@PathVariable Long id) {
        TestimonialResponseDto testimonial =
                testimonialService.getTestimonialById(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Testimonial fetched successfully",
                        testimonial));
    }

    //  ADMIN - Get all testimonials
    // GET /api/testimonials/admin/all
    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponse<List<TestimonialResponseDto>>>
            getAllTestimonials() {
        List<TestimonialResponseDto> testimonials =
                testimonialService.getAllTestimonials();
        return ResponseEntity.ok(
                ApiResponse.success(
                        "All testimonials fetched successfully",
                        testimonials));
    }

    //  ADMIN - Create testimonial
    // POST /api/testimonials/admin
    @PostMapping("/admin")
    public ResponseEntity<ApiResponse<TestimonialResponseDto>>
            createTestimonial(
                @Valid @RequestBody TestimonialRequestDto dto) {
        TestimonialResponseDto testimonial =
                testimonialService.createTestimonial(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Testimonial created successfully",
                        testimonial));
    }

    //  ADMIN - Update testimonial
    // PUT /api/testimonials/admin/{id}
    @PutMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<TestimonialResponseDto>>
            updateTestimonial(
                @PathVariable Long id,
                @Valid @RequestBody TestimonialRequestDto dto) {
        TestimonialResponseDto testimonial =
                testimonialService.updateTestimonial(id, dto);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Testimonial updated successfully",
                        testimonial));
    }

    //  ADMIN - Toggle active
    // PATCH /api/testimonials/admin/{id}/toggle-active
    @PatchMapping("/admin/{id}/toggle-active")
    public ResponseEntity<ApiResponse<TestimonialResponseDto>>
            toggleActive(@PathVariable Long id) {
        TestimonialResponseDto testimonial =
                testimonialService.toggleActive(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Testimonial active status toggled",
                        testimonial));
    }

    //  ADMIN - Toggle featured
    // PATCH /api/testimonials/admin/{id}/toggle-featured
    @PatchMapping("/admin/{id}/toggle-featured")
    public ResponseEntity<ApiResponse<TestimonialResponseDto>>
            toggleFeatured(@PathVariable Long id) {
        TestimonialResponseDto testimonial =
                testimonialService.toggleFeatured(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Testimonial featured status toggled",
                        testimonial));
    }

    //  ADMIN - Delete testimonial
    // DELETE /api/testimonials/admin/{id}
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<?>>
            deleteTestimonial(@PathVariable Long id) {
        testimonialService.deleteTestimonial(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Testimonial deleted successfully"));
    }

    //  ADMIN - Get by rating
    // GET /api/testimonials/admin/rating?rating=5
    @GetMapping("/admin/rating")
    public ResponseEntity<ApiResponse<List<TestimonialResponseDto>>>
            getByRating(@RequestParam int rating) {
        List<TestimonialResponseDto> testimonials =
                testimonialService.getByRating(rating);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Testimonials fetched successfully",
                        testimonials));
    }

    // ADMIN - Get by company
    // GET /api/testimonials/admin/company?name=TCS
    @GetMapping("/admin/company")
    public ResponseEntity<ApiResponse<List<TestimonialResponseDto>>>
            getByCompany(@RequestParam String name) {
        List<TestimonialResponseDto> testimonials =
                testimonialService.getByCompany(name);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Testimonials fetched successfully",
                        testimonials));
    }

    //  ADMIN - Get by course
    // GET /api/testimonials/admin/course?name=Java
    @GetMapping("/admin/course")
    public ResponseEntity<ApiResponse<List<TestimonialResponseDto>>>
            getByCourse(@RequestParam String name) {
        List<TestimonialResponseDto> testimonials =
                testimonialService.getByCourse(name);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Testimonials fetched successfully",
                        testimonials));
    }
}