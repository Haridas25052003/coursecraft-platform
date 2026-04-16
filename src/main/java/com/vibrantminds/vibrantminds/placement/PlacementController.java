package com.vibrantminds.vibrantminds.placement;

import com.vibrantminds.vibrantminds.exception.ApiResponse;
import com.vibrantminds.vibrantminds.placement.dto.PlacementRequestDto;
import com.vibrantminds.vibrantminds.placement.dto.PlacementResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/placements")
@RequiredArgsConstructor
public class PlacementController {

    private final PlacementService placementService;

    //  PUBLIC - Get all active placements
    // GET /api/placements
    @GetMapping
    public ResponseEntity<ApiResponse<List<PlacementResponseDto>>>
            getAllActivePlacements() {
        List<PlacementResponseDto> placements =
                placementService.getAllActivePlacements();
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Placements fetched successfully",
                        placements));
    }

    //  PUBLIC - Get placement by id
    // GET /api/placements/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PlacementResponseDto>>
            getPlacementById(@PathVariable Long id) {
        PlacementResponseDto placement =
                placementService.getPlacementById(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Placement fetched successfully",
                        placement));
    }

    // ADMIN - Get all placements
    // GET /api/placements/admin/all
    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponse<List<PlacementResponseDto>>>
            getAllPlacements() {
        List<PlacementResponseDto> placements =
                placementService.getAllPlacements();
        return ResponseEntity.ok(
                ApiResponse.success(
                        "All placements fetched successfully",
                        placements));
    }

    //  ADMIN - Create placement
    // POST /api/placements/admin
    @PostMapping("/admin")
    public ResponseEntity<ApiResponse<PlacementResponseDto>>
            createPlacement(
                @Valid @RequestBody PlacementRequestDto dto) {
        PlacementResponseDto placement =
                placementService.createPlacement(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Placement created successfully",
                        placement));
    }

    //  ADMIN - Update placement
    // PUT /api/placements/admin/{id}
    @PutMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<PlacementResponseDto>>
            updatePlacement(
                @PathVariable Long id,
                @Valid @RequestBody PlacementRequestDto dto) {
        PlacementResponseDto placement =
                placementService.updatePlacement(id, dto);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Placement updated successfully",
                        placement));
    }

    //  ADMIN - Update placement status
    // PATCH /api/placements/admin/{id}/status?status=JOINED
    @PatchMapping("/admin/{id}/status")
    public ResponseEntity<ApiResponse<PlacementResponseDto>>
            updateStatus(
                @PathVariable Long id,
                @RequestParam String status) {
        PlacementResponseDto placement =
                placementService.updatePlacementStatus(id, status);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Status updated successfully", placement));
    }

    //  ADMIN - Toggle active/inactive
    // PATCH /api/placements/admin/{id}/toggle
    @PatchMapping("/admin/{id}/toggle")
    public ResponseEntity<ApiResponse<PlacementResponseDto>>
            toggleActive(@PathVariable Long id) {
        PlacementResponseDto placement =
                placementService.togglePlacementActive(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Placement status toggled successfully",
                        placement));
    }

    //  ADMIN - Delete placement
    // DELETE /api/placements/admin/{id}
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<?>>
            deletePlacement(@PathVariable Long id) {
        placementService.deletePlacement(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Placement deleted successfully"));
    }

    //  ADMIN - Get placements by candidate
    // GET /api/placements/admin/candidate/{candidateId}
    @GetMapping("/admin/candidate/{candidateId}")
    public ResponseEntity<ApiResponse<List<PlacementResponseDto>>>
            getByCandidate(@PathVariable Long candidateId) {
        List<PlacementResponseDto> placements =
                placementService.getPlacementsByCandidate(
                        candidateId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Placements fetched successfully",
                        placements));
    }

    //  ADMIN - Get placements by company
    // GET /api/placements/admin/company/{companyId}
    @GetMapping("/admin/company/{companyId}")
    public ResponseEntity<ApiResponse<List<PlacementResponseDto>>>
            getByCompany(@PathVariable Long companyId) {
        List<PlacementResponseDto> placements =
                placementService.getPlacementsByCompany(companyId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Placements fetched successfully",
                        placements));
    }

    //  ADMIN - Get placements by status
    // GET /api/placements/admin/status?status=PLACED
    @GetMapping("/admin/status")
    public ResponseEntity<ApiResponse<List<PlacementResponseDto>>>
            getByStatus(@RequestParam String status) {
        List<PlacementResponseDto> placements =
                placementService.getPlacementsByStatus(status);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Placements fetched successfully",
                        placements));
    }

    //  ADMIN - Get placements by type
    // GET /api/placements/admin/type?type=Campus
    @GetMapping("/admin/type")
    public ResponseEntity<ApiResponse<List<PlacementResponseDto>>>
            getByType(@RequestParam String type) {
        List<PlacementResponseDto> placements =
                placementService.getPlacementsByType(type);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Placements fetched successfully",
                        placements));
    }

    //  ADMIN - Count by company
    // GET /api/placements/admin/count/company/{companyId}
    @GetMapping("/admin/count/company/{companyId}")
    public ResponseEntity<ApiResponse<Long>>
            countByCompany(@PathVariable Long companyId) {
        long count = placementService.countByCompany(companyId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Count fetched successfully", count));
    }

    //  ADMIN - Count by status
    // GET /api/placements/admin/count/status?status=PLACED
    @GetMapping("/admin/count/status")
    public ResponseEntity<ApiResponse<Long>>
            countByStatus(@RequestParam String status) {
        long count = placementService.countByStatus(status);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Count fetched successfully", count));
    }
}