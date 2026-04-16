package com.vibrantminds.vibrantminds.placement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlacementRepository
        extends JpaRepository<PlacementEntity, Long> {

    // Get all placements by candidate
    List<PlacementEntity> findByCandidateId(Long candidateId);

    // Get all placements by company
    List<PlacementEntity> findByCompanyId(Long companyId);

    // Get all placements by status
    List<PlacementEntity> findByStatus(String status);

    // Get all placements by type
    List<PlacementEntity> findByPlacementType(String placementType);

    // Get all active placements
    List<PlacementEntity> findByActiveTrue();

    // Count placements by company
    long countByCompanyId(Long companyId);

    // Count placements by status
    long countByStatus(String status);

    // Check if candidate already placed in company
    boolean existsByCandidateIdAndCompanyId(
            Long candidateId, Long companyId);
}