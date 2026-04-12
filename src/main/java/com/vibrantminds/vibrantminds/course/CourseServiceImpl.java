package com.vibrantminds.vibrantminds.course;

import com.vibrantminds.vibrantminds.course.dto.CourseRequestDto;
import com.vibrantminds.vibrantminds.course.dto.CourseResponseDto;
import com.vibrantminds.vibrantminds.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    //  Create Course (Admin)
    @Override
    public CourseResponseDto createCourse(CourseRequestDto dto) {
        CourseEntity course = new CourseEntity();
        mapToEntity(dto, course);
        CourseEntity saved = courseRepository.save(course);
        return mapToResponse(saved);
    }

    //  Update Course (Admin)
    @Override
    public CourseResponseDto updateCourse(Long id, CourseRequestDto dto) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course", id));
        mapToEntity(dto, course);
        CourseEntity updated = courseRepository.save(course);
        return mapToResponse(updated);
    }

    //  Delete Course (Admin)
    @Override
    public void deleteCourse(Long id) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course", id));
        courseRepository.delete(course);
    }

    //  Toggle Active/Inactive (Admin)
    @Override
    public CourseResponseDto toggleCourseStatus(Long id) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course", id));
        course.setActive(!course.isActive());
        CourseEntity updated = courseRepository.save(course);
        return mapToResponse(updated);
    }

    //  Get All Active Courses (Public)
    @Override
    public List<CourseResponseDto> getAllActiveCourses() {
        return courseRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get All Courses (Admin)
    @Override
    public List<CourseResponseDto> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get Course By ID (Public)
    @Override
    public CourseResponseDto getCourseById(Long id) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course", id));
        return mapToResponse(course);
    }

    //  Search by Title (Public)
    @Override
    public List<CourseResponseDto> searchByTitle(String title) {
        return courseRepository
                .findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Search by Mode (Public)
    @Override
    public List<CourseResponseDto> searchByMode(String mode) {
        return courseRepository.findByMode(mode)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Search by Duration (Public)
    @Override
    public List<CourseResponseDto> searchByDuration(String duration) {
        return courseRepository.findByDuration(duration)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ── Helper Methods ─────────────────────────────

    private void mapToEntity(CourseRequestDto dto, CourseEntity course) {
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setDuration(dto.getDuration());
        course.setMode(dto.getMode());
        course.setFees(dto.getFees());
        course.setSyllabus(dto.getSyllabus());
        course.setEligibility(dto.getEligibility());
        course.setCertificate(dto.getCertificate());
        course.setJobAssistance(dto.getJobAssistance());
        course.setThumbnail(dto.getThumbnail());
        course.setActive(dto.isActive());
    }

    private CourseResponseDto mapToResponse(CourseEntity course) {
        CourseResponseDto dto = new CourseResponseDto();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setDuration(course.getDuration());
        dto.setMode(course.getMode());
        dto.setFees(course.getFees());
        dto.setSyllabus(course.getSyllabus());
        dto.setEligibility(course.getEligibility());
        dto.setCertificate(course.getCertificate());
        dto.setJobAssistance(course.getJobAssistance());
        dto.setThumbnail(course.getThumbnail());
        dto.setActive(course.isActive());
        dto.setCreatedAt(course.getCreatedAt());
        dto.setUpdatedAt(course.getUpdatedAt());
        return dto;
    }
}