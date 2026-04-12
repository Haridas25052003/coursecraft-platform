package com.vibrantminds.vibrantminds.course;

import com.vibrantminds.vibrantminds.course.dto.CourseRequestDto;
import com.vibrantminds.vibrantminds.course.dto.CourseResponseDto;
import com.vibrantminds.vibrantminds.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    //  PUBLIC - Get all active courses
    // GET /api/courses
    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponseDto>>>
            getAllActiveCourses() {
        List<CourseResponseDto> courses =
                courseService.getAllActiveCourses();
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Courses fetched successfully", courses));
    }

    //  PUBLIC - Get course by id
    // GET /api/courses/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDto>>
            getCourseById(@PathVariable Long id) {
        CourseResponseDto course = courseService.getCourseById(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Course fetched successfully", course));
    }

    //  PUBLIC - Search courses
    // GET /api/courses/search?title=java
    // GET /api/courses/search?mode=Online
    // GET /api/courses/search?duration=3 Months
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CourseResponseDto>>>
            searchCourses(
                @RequestParam(required = false) String title,
                @RequestParam(required = false) String mode,
                @RequestParam(required = false) String duration) {

        List<CourseResponseDto> courses;

        if (title != null) {
            courses = courseService.searchByTitle(title);
        } else if (mode != null) {
            courses = courseService.searchByMode(mode);
        } else if (duration != null) {
            courses = courseService.searchByDuration(duration);
        } else {
            courses = courseService.getAllActiveCourses();
        }

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Courses fetched successfully", courses));
    }

    // ADMIN - Get all courses including inactive
    // GET /api/courses/admin/all
    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponse<List<CourseResponseDto>>>
            getAllCourses() {
        List<CourseResponseDto> courses =
                courseService.getAllCourses();
        return ResponseEntity.ok(
                ApiResponse.success(
                        "All courses fetched successfully", courses));
    }

    //  ADMIN - Create course
    // POST /api/courses/admin
    @PostMapping("/admin")
    public ResponseEntity<ApiResponse<CourseResponseDto>>
            createCourse(@Valid @RequestBody CourseRequestDto dto) {
        CourseResponseDto course = courseService.createCourse(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Course created successfully", course));
    }

    //  ADMIN - Update course
    // PUT /api/courses/admin/{id}
    @PutMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDto>>
            updateCourse(
                @PathVariable Long id,
                @Valid @RequestBody CourseRequestDto dto) {
        CourseResponseDto course =
                courseService.updateCourse(id, dto);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Course updated successfully", course));
    }

    //  ADMIN - Toggle course active/inactive
    // PATCH /api/courses/admin/{id}/toggle
    @PatchMapping("/admin/{id}/toggle")
    public ResponseEntity<ApiResponse<CourseResponseDto>>
            toggleCourseStatus(@PathVariable Long id) {
        CourseResponseDto course =
                courseService.toggleCourseStatus(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Course status updated successfully", course));
    }

    //  ADMIN - Delete course
    // DELETE /api/courses/admin/{id}
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<?>>
            deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok(
                ApiResponse.success("Course deleted successfully"));
    }
}