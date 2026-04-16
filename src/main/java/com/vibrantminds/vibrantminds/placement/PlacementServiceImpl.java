package com.vibrantminds.vibrantminds.placement;

import com.vibrantminds.vibrantminds.candidate.CandidateEntity;
import com.vibrantminds.vibrantminds.candidate.CandidateRepository;
import com.vibrantminds.vibrantminds.company.CompanyEntity;
import com.vibrantminds.vibrantminds.company.CompanyRepository;
import com.vibrantminds.vibrantminds.exception.ResourceNotFoundException;
import com.vibrantminds.vibrantminds.placement.dto.PlacementRequestDto;
import com.vibrantminds.vibrantminds.placement.dto.PlacementResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlacementServiceImpl implements PlacementService {

    private final PlacementRepository placementRepository;
    private final CandidateRepository candidateRepository;
    private final CompanyRepository companyRepository;

    //  Create Placement (Admin)
    @Override
    public PlacementResponseDto createPlacement(
            PlacementRequestDto dto) {

        CandidateEntity candidate = candidateRepository
                .findById(dto.getCandidateId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate", dto.getCandidateId()));

        CompanyEntity company = companyRepository
                .findById(dto.getCompanyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Company", dto.getCompanyId()));

        PlacementEntity placement = new PlacementEntity();
        placement.setCandidate(candidate);
        placement.setCompany(company);
        mapToEntity(dto, placement);

        PlacementEntity saved = placementRepository.save(placement);
        return mapToResponse(saved);
    }

    // Update Placement (Admin)
    @Override
    public PlacementResponseDto updatePlacement(
            Long id, PlacementRequestDto dto) {

        PlacementEntity placement = placementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Placement", id));

        CandidateEntity candidate = candidateRepository
                .findById(dto.getCandidateId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate", dto.getCandidateId()));

        CompanyEntity company = companyRepository
                .findById(dto.getCompanyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Company", dto.getCompanyId()));

        placement.setCandidate(candidate);
        placement.setCompany(company);
        mapToEntity(dto, placement);

        PlacementEntity updated = placementRepository.save(placement);
        return mapToResponse(updated);
    }

    //  Delete Placement (Admin)
    @Override
    public void deletePlacement(Long id) {
        PlacementEntity placement = placementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Placement", id));
        placementRepository.delete(placement);
    }

    //  Update Placement Status (Admin)
    @Override
    public PlacementResponseDto updatePlacementStatus(
            Long id, String status) {

        PlacementEntity placement = placementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Placement", id));

        List<String> validStatuses = List.of(
                "PLACED",
                "JOINED",
                "LEFT",
                "OFFER_DROPPED"
        );

        if (!validStatuses.contains(status.toUpperCase())) {
            throw new RuntimeException(
                    "Invalid status. Valid values : "
                            + validStatuses);
        }

        placement.setStatus(status.toUpperCase());
        PlacementEntity updated = placementRepository.save(placement);
        return mapToResponse(updated);
    }

    //  Toggle Active/Inactive (Admin)
    @Override
    public PlacementResponseDto togglePlacementActive(Long id) {
        PlacementEntity placement = placementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Placement", id));
        placement.setActive(!placement.isActive());
        PlacementEntity updated = placementRepository.save(placement);
        return mapToResponse(updated);
    }

    //  Get All Active Placements (Public)
    @Override
    public List<PlacementResponseDto> getAllActivePlacements() {
        return placementRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get All Placements (Admin)
    @Override
    public List<PlacementResponseDto> getAllPlacements() {
        return placementRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get Placement By ID
    @Override
    public PlacementResponseDto getPlacementById(Long id) {
        PlacementEntity placement = placementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Placement", id));
        return mapToResponse(placement);
    }

    //  Get Placements By Candidate
    @Override
    public List<PlacementResponseDto> getPlacementsByCandidate(
            Long candidateId) {
        return placementRepository.findByCandidateId(candidateId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get Placements By Company
    @Override
    public List<PlacementResponseDto> getPlacementsByCompany(
            Long companyId) {
        return placementRepository.findByCompanyId(companyId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get Placements By Status
    @Override
    public List<PlacementResponseDto> getPlacementsByStatus(
            String status) {
        return placementRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get Placements By Type
    @Override
    public List<PlacementResponseDto> getPlacementsByType(
            String placementType) {
        return placementRepository.findByPlacementType(placementType)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Count By Company
    @Override
    public long countByCompany(Long companyId) {
        return placementRepository.countByCompanyId(companyId);
    }

    //  Count By Status
    @Override
    public long countByStatus(String status) {
        return placementRepository.countByStatus(status);
    }

    // ── Helper Methods ──────────────────────────────

    private void mapToEntity(
            PlacementRequestDto dto, PlacementEntity placement) {
        placement.setJobTitle(dto.getJobTitle());
        placement.setPackageOffered(dto.getPackageOffered());
        placement.setJoiningDate(dto.getJoiningDate());
        placement.setPlacementType(dto.getPlacementType());
        placement.setLocation(dto.getLocation());
        placement.setStatus(dto.getStatus().toUpperCase());
        placement.setRemarks(dto.getRemarks());
        placement.setActive(dto.isActive());
    }

    private PlacementResponseDto mapToResponse(
            PlacementEntity placement) {

        PlacementResponseDto dto = new PlacementResponseDto();
        dto.setId(placement.getId());

        // Candidate info
        dto.setCandidateId(placement.getCandidate().getId());
        dto.setCandidateName(placement.getCandidate().getName());
        dto.setCandidateEmail(placement.getCandidate().getEmail());
        dto.setCandidatePhone(placement.getCandidate().getMobile());

        // Company info
        dto.setCompanyId(placement.getCompany().getId());
        dto.setCompanyName(placement.getCompany().getName());
        dto.setCompanyIndustry(placement.getCompany().getIndustry());
        dto.setCompanyCity(placement.getCompany().getCity());

        // Placement info
        dto.setJobTitle(placement.getJobTitle());
        dto.setPackageOffered(placement.getPackageOffered());
        dto.setJoiningDate(placement.getJoiningDate());
        dto.setPlacementType(placement.getPlacementType());
        dto.setLocation(placement.getLocation());
        dto.setStatus(placement.getStatus());
        dto.setRemarks(placement.getRemarks());
        dto.setActive(placement.isActive());
        dto.setPlacedAt(placement.getPlacedAt());
        dto.setUpdatedAt(placement.getUpdatedAt());

        return dto;
    }
}