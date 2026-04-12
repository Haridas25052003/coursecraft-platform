package com.vibrantminds.vibrantminds.course;

import com.vibrantminds.vibrantminds.course.dto.CourseRequestDto;
import com.vibrantminds.vibrantminds.course.dto.CourseResponseDto;
import java.util.List;

public interface CourseService {

    // Admin operations
    CourseResponseDto createCourse(CourseRequestDto dto);
    CourseResponseDto updateCourse(Long id, CourseRequestDto dto);
    void deleteCourse(Long id);
    CourseResponseDto toggleCourseStatus(Long id);

    // Public operations
    List<CourseResponseDto> getAllActiveCourses();
    List<CourseResponseDto> getAllCourses();
    CourseResponseDto getCourseById(Long id);

    // Search operations
    List<CourseResponseDto> searchByTitle(String title);
    List<CourseResponseDto> searchByMode(String mode);
    List<CourseResponseDto> searchByDuration(String duration);
}