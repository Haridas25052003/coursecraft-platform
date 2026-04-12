package com.vibrantminds.vibrantminds.candidate;

import com.vibrantminds.vibrantminds.candidate.dto.CandidateLoginDto;
import com.vibrantminds.vibrantminds.candidate.dto.CandidateRequestDto;
import com.vibrantminds.vibrantminds.candidate.dto.CandidateResponseDto;
import com.vibrantminds.vibrantminds.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/candidate")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    //  PUBLIC - Register
    // POST /api/candidate/register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CandidateResponseDto>>
            register(@Valid @RequestBody CandidateRequestDto dto) {

        CandidateResponseDto response =
                candidateService.register(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Registration successful", response));
    }

    //  PUBLIC - Login
    // POST /api/candidate/login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(
            @Valid @RequestBody CandidateLoginDto dto) {

        String token = candidateService.login(dto);
        return ResponseEntity.ok(
                ApiResponse.success("Login successful", token));
    }

    //  CANDIDATE - Get own profile
    // GET /api/candidate/profile
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<CandidateResponseDto>>
            getProfile(@AuthenticationPrincipal
                       UserDetails userDetails) {

        CandidateResponseDto profile =
                candidateService.getProfile(userDetails.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Profile fetched successfully", profile));
    }

    //  CANDIDATE - Update own profile
    // PUT /api/candidate/profile
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<CandidateResponseDto>>
            updateProfile(
                @AuthenticationPrincipal UserDetails userDetails,
                @Valid @RequestBody CandidateRequestDto dto) {

        CandidateResponseDto updated = candidateService
                .updateProfile(userDetails.getUsername(), dto);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Profile updated successfully", updated));
    }

    //  ADMIN - Get all candidates
    // GET /api/candidate/admin/all
    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponse<List<CandidateResponseDto>>>
            getAllCandidates() {

        List<CandidateResponseDto> candidates =
                candidateService.getAllCandidates();
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Candidates fetched successfully", candidates));
    }

    //  ADMIN - Get candidate by id
    // GET /api/candidate/admin/{id}
    @GetMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<CandidateResponseDto>>
            getCandidateById(@PathVariable Long id) {

        CandidateResponseDto candidate =
                candidateService.getCandidateById(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Candidate fetched successfully", candidate));
    }

    //  ADMIN - Search candidates
    // GET /api/candidate/admin/search?name=rahul
    // GET /api/candidate/admin/search?skill=java
    // GET /api/candidate/admin/search?city=pune
    @GetMapping("/admin/search")
    public ResponseEntity<ApiResponse<List<CandidateResponseDto>>>
            searchCandidates(
                @RequestParam(required = false) String name,
                @RequestParam(required = false) String skill,
                @RequestParam(required = false) String city) {

        List<CandidateResponseDto> candidates;

        if (name != null) {
            candidates = candidateService.searchByName(name);
        } else if (skill != null) {
            candidates = candidateService.searchBySkill(skill);
        } else if (city != null) {
            candidates = candidateService.searchByCity(city);
        } else {
            candidates = candidateService.getAllCandidates();
        }

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Candidates fetched successfully", candidates));
    }

    //  ADMIN - Delete candidate
    // DELETE /api/candidate/admin/{id}
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCandidate(
            @PathVariable Long id) {

        candidateService.deleteCandidate(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Candidate deleted successfully"));
    }
}