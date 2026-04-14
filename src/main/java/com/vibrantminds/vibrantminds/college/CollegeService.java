package com.vibrantminds.vibrantminds.college;

import com.vibrantminds.vibrantminds.college.dto.CollegeRequestDto;
import com.vibrantminds.vibrantminds.college.dto.CollegeResponseDto;
import java.util.List;

public interface CollegeService {

    // Admin operations
    CollegeResponseDto createCollege(CollegeRequestDto dto);
    CollegeResponseDto updateCollege(Long id, CollegeRequestDto dto);
    void deleteCollege(Long id);
    CollegeResponseDto toggleCollegeStatus(Long id);

    // Public operations
    List<CollegeResponseDto> getAllActiveColleges();
    List<CollegeResponseDto> getAllColleges();
    CollegeResponseDto getCollegeById(Long id);

    // Search operations
    List<CollegeResponseDto> searchByName(String name);
    List<CollegeResponseDto> searchByCity(String city);
    List<CollegeResponseDto> searchByState(String state);
    List<CollegeResponseDto> searchByType(String type);
}