package com.vibrantminds.vibrantminds.college;

import com.vibrantminds.vibrantminds.college.dto.CollegeRequestDto;
import com.vibrantminds.vibrantminds.college.dto.CollegeResponseDto;
import com.vibrantminds.vibrantminds.exception.ApiResponse;
import com.vibrantminds.vibrantminds.college.CollegeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/colleges")
@RequiredArgsConstructor
public class CollegeController {

    private final CollegeService collegeService;

    //  PUBLIC - Get all active colleges
    // GET /api/colleges
    @GetMapping
    public ResponseEntity<ApiResponse<List<CollegeResponseDto>>>
            getAllActiveColleges() {
        List<CollegeResponseDto> colleges =
                collegeService.getAllActiveColleges();
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Colleges fetched successfully", colleges));
    }

    //  PUBLIC - Get college by id
    // GET /api/colleges/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CollegeResponseDto>>
            getCollegeById(@PathVariable Long id) {
        CollegeResponseDto college =
                collegeService.getCollegeById(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "College fetched successfully", college));
    }

    //  PUBLIC - Search colleges
    // GET /api/colleges/search?name=MIT
    // GET /api/colleges/search?city=Pune
    // GET /api/colleges/search?state=Maharashtra
    // GET /api/colleges/search?type=Engineering
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CollegeResponseDto>>>
            searchColleges(
                @RequestParam(required = false) String name,
                @RequestParam(required = false) String city,
                @RequestParam(required = false) String state,
                @RequestParam(required = false) String type) {

        List<CollegeResponseDto> colleges;

        if (name != null) {
            colleges = collegeService.searchByName(name);
        } else if (city != null) {
            colleges = collegeService.searchByCity(city);
        } else if (state != null) {
            colleges = collegeService.searchByState(state);
        } else if (type != null) {
            colleges = collegeService.searchByType(type);
        } else {
            colleges = collegeService.getAllActiveColleges();
        }

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Colleges fetched successfully", colleges));
    }

    //  ADMIN - Get all colleges including inactive
    // GET /api/colleges/admin/all
    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponse<List<CollegeResponseDto>>>
            getAllColleges() {
        List<CollegeResponseDto> colleges =
                collegeService.getAllColleges();
        return ResponseEntity.ok(
                ApiResponse.success(
                        "All colleges fetched successfully",
                        colleges));
    }

    // ADMIN - Create college
    // POST /api/colleges/admin
    @PostMapping("/admin")
    public ResponseEntity<ApiResponse<CollegeResponseDto>>
            createCollege(
                @Valid @RequestBody CollegeRequestDto dto) {
        CollegeResponseDto college =
                collegeService.createCollege(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "College created successfully", college));
    }

    //  ADMIN - Update college
    // PUT /api/colleges/admin/{id}
    @PutMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<CollegeResponseDto>>
            updateCollege(
                @PathVariable Long id,
                @Valid @RequestBody CollegeRequestDto dto) {
        CollegeResponseDto college =
                collegeService.updateCollege(id, dto);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "College updated successfully", college));
    }

    //  ADMIN - Toggle college status
    // PATCH /api/colleges/admin/{id}/toggle
    @PatchMapping("/admin/{id}/toggle")
    public ResponseEntity<ApiResponse<CollegeResponseDto>>
            toggleCollegeStatus(@PathVariable Long id) {
        CollegeResponseDto college =
                collegeService.toggleCollegeStatus(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "College status updated successfully",
                        college));
    }

    //  ADMIN - Delete college
    // DELETE /api/colleges/admin/{id}
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<?>>
            deleteCollege(@PathVariable Long id) {
        collegeService.deleteCollege(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "College deleted successfully"));
    }
}