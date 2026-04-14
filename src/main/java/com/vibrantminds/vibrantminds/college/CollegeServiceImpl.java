package com.vibrantminds.vibrantminds.college;

import com.vibrantminds.vibrantminds.college.dto.CollegeRequestDto;
import com.vibrantminds.vibrantminds.college.dto.CollegeResponseDto;
import com.vibrantminds.vibrantminds.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollegeServiceImpl implements CollegeService {

    private final CollegeRepository collegeRepository;

    //  Create College (Admin)
    @Override
    public CollegeResponseDto createCollege(CollegeRequestDto dto) {

        if (collegeRepository.existsByName(dto.getName())) {
            throw new RuntimeException(
                    "College already exists with name : "
                            + dto.getName());
        }

        CollegeEntity college = new CollegeEntity();
        mapToEntity(dto, college);
        CollegeEntity saved = collegeRepository.save(college);
        return mapToResponse(saved);
    }

    //  Update College (Admin)
    @Override
    public CollegeResponseDto updateCollege(
            Long id, CollegeRequestDto dto) {

        CollegeEntity college = collegeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("College", id));

        mapToEntity(dto, college);
        CollegeEntity updated = collegeRepository.save(college);
        return mapToResponse(updated);
    }

    //  Delete College (Admin)
    @Override
    public void deleteCollege(Long id) {

        CollegeEntity college = collegeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("College", id));

        collegeRepository.delete(college);
    }

    //  Toggle Active/Inactive (Admin)
    @Override
    public CollegeResponseDto toggleCollegeStatus(Long id) {

        CollegeEntity college = collegeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("College", id));

        college.setActive(!college.isActive());
        CollegeEntity updated = collegeRepository.save(college);
        return mapToResponse(updated);
    }

    //  Get All Active Colleges (Public)
    @Override
    public List<CollegeResponseDto> getAllActiveColleges() {
        return collegeRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get All Colleges (Admin)
    @Override
    public List<CollegeResponseDto> getAllColleges() {
        return collegeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get College By ID
    @Override
    public CollegeResponseDto getCollegeById(Long id) {
        CollegeEntity college = collegeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("College", id));
        return mapToResponse(college);
    }

    //  Search by Name
    @Override
    public List<CollegeResponseDto> searchByName(String name) {
        return collegeRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Search by City
    @Override
    public List<CollegeResponseDto> searchByCity(String city) {
        return collegeRepository.findByCity(city)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Search by State
    @Override
    public List<CollegeResponseDto> searchByState(String state) {
        return collegeRepository.findByState(state)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Search by Type
    @Override
    public List<CollegeResponseDto> searchByType(String type) {
        return collegeRepository.findByType(type)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ── Helper Methods ──────────────────────────────

    private void mapToEntity(
            CollegeRequestDto dto, CollegeEntity college) {
        college.setName(dto.getName());
        college.setCity(dto.getCity());
        college.setState(dto.getState());
        college.setAddress(dto.getAddress());
        college.setContactPerson(dto.getContactPerson());
        college.setContactEmail(dto.getContactEmail());
        college.setContactPhone(dto.getContactPhone());
        college.setWebsite(dto.getWebsite());
        college.setLogo(dto.getLogo());
        college.setDescription(dto.getDescription());
        college.setAffiliatedTo(dto.getAffiliatedTo());
        college.setType(dto.getType());
        college.setActive(dto.isActive());
    }

    private CollegeResponseDto mapToResponse(CollegeEntity college) {
        CollegeResponseDto dto = new CollegeResponseDto();
        dto.setId(college.getId());
        dto.setName(college.getName());
        dto.setCity(college.getCity());
        dto.setState(college.getState());
        dto.setAddress(college.getAddress());
        dto.setContactPerson(college.getContactPerson());
        dto.setContactEmail(college.getContactEmail());
        dto.setContactPhone(college.getContactPhone());
        dto.setWebsite(college.getWebsite());
        dto.setLogo(college.getLogo());
        dto.setDescription(college.getDescription());
        dto.setAffiliatedTo(college.getAffiliatedTo());
        dto.setType(college.getType());
        dto.setActive(college.isActive());
        dto.setCreatedAt(college.getCreatedAt());
        dto.setUpdatedAt(college.getUpdatedAt());
        return dto;
    }
}