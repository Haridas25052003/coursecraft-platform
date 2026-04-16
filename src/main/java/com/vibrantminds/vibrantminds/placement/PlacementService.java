package com.vibrantminds.vibrantminds.placement;

import com.vibrantminds.vibrantminds.placement.dto.PlacementRequestDto;
import com.vibrantminds.vibrantminds.placement.dto.PlacementResponseDto;
import java.util.List;

public interface PlacementService {

    // Admin operations
    PlacementResponseDto createPlacement(PlacementRequestDto dto);
    PlacementResponseDto updatePlacement(
            Long id, PlacementRequestDto dto);
    void deletePlacement(Long id);
    PlacementResponseDto updatePlacementStatus(
            Long id, String status);
    PlacementResponseDto togglePlacementActive(Long id);

    // Public operations
    List<PlacementResponseDto> getAllActivePlacements();
    List<PlacementResponseDto> getAllPlacements();
    PlacementResponseDto getPlacementById(Long id);

    // Filter operations
    List<PlacementResponseDto> getPlacementsByCandidate(
            Long candidateId);
    List<PlacementResponseDto> getPlacementsByCompany(
            Long companyId);
    List<PlacementResponseDto> getPlacementsByStatus(String status);
    List<PlacementResponseDto> getPlacementsByType(
            String placementType);

    // Stats
    long countByCompany(Long companyId);
    long countByStatus(String status);
}