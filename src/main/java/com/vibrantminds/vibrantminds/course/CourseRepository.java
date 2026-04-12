package com.vibrantminds.vibrantminds.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository
        extends JpaRepository<CourseEntity, Long> {

    // Get all active courses
    List<CourseEntity> findByActiveTrue();

    // Search by title
    List<CourseEntity> findByTitleContainingIgnoreCase(String title);

    // Search by mode
    List<CourseEntity> findByMode(String mode);

    // Search by duration
    List<CourseEntity> findByDuration(String duration);
}