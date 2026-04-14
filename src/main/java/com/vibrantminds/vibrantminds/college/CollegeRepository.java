package com.vibrantminds.vibrantminds.college;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CollegeRepository
        extends JpaRepository<CollegeEntity, Long> {

    List<CollegeEntity> findByActiveTrue();

    List<CollegeEntity> findByCity(String city);

    List<CollegeEntity> findByState(String state);

    List<CollegeEntity> findByType(String type);

    List<CollegeEntity> findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);
}