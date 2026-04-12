package com.vibrantminds.vibrantminds.candidate;

import com.vibrantminds.vibrantminds.candidate.dto.CandidateLoginDto;
import com.vibrantminds.vibrantminds.candidate.dto.CandidateRequestDto;
import com.vibrantminds.vibrantminds.candidate.dto.CandidateResponseDto;
import com.vibrantminds.vibrantminds.exception.ResourceNotFoundException;
import com.vibrantminds.vibrantminds.exception.UnauthorizedException;
import com.vibrantminds.vibrantminds.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    //  Register (Public)
    @Override
    public CandidateResponseDto register(CandidateRequestDto dto) {

        if (candidateRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException(
                    "Email already registered");
        }

        if (candidateRepository.existsByMobile(dto.getMobile())) {
            throw new RuntimeException(
                    "Mobile number already registered");
        }

        CandidateEntity candidate = new CandidateEntity();
        mapToEntity(dto, candidate);
        candidate.setPassword(
                passwordEncoder.encode(dto.getPassword()));
        candidate.setRole("ROLE_CANDIDATE");

        CandidateEntity saved = candidateRepository.save(candidate);
        return mapToResponse(saved);
    }

    //  Login (Public)
    @Override
    public String login(CandidateLoginDto dto) {

        CandidateEntity candidate = candidateRepository
                .findByEmail(dto.getEmail())
                .orElseThrow(() -> new UnauthorizedException(
                        "Invalid email or password"));

        if (!passwordEncoder.matches(
                dto.getPassword(), candidate.getPassword())) {
            throw new UnauthorizedException(
                    "Invalid email or password");
        }

        if (!candidate.isActive()) {
            throw new UnauthorizedException(
                    "Your account is deactivated. Contact admin.");
        }

        return jwtUtil.generateToken(
                candidate.getEmail(), candidate.getRole());
    }

    //  Get Profile (Candidate)
    @Override
    public CandidateResponseDto getProfile(String email) {
        CandidateEntity candidate = candidateRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Candidate not found with email : " + email));
        return mapToResponse(candidate);
    }

    //  Update Profile (Candidate)
    @Override
    public CandidateResponseDto updateProfile(
            String email, CandidateRequestDto dto) {

        CandidateEntity candidate = candidateRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Candidate not found with email : " + email));

        // Update fields except email and password
        candidate.setName(dto.getName());
        candidate.setMobile(dto.getMobile());
        candidate.setQualification(dto.getQualification());
        candidate.setSkills(dto.getSkills());
        candidate.setExperience(dto.getExperience());
        candidate.setCurrentCity(dto.getCurrentCity());
        candidate.setResumeLink(dto.getResumeLink());
        candidate.setLinkedinProfile(dto.getLinkedinProfile());
        candidate.setGithubProfile(dto.getGithubProfile());

        CandidateEntity updated = candidateRepository.save(candidate);
        return mapToResponse(updated);
    }

    //  Get All Candidates (Admin)
    @Override
    public List<CandidateResponseDto> getAllCandidates() {
        return candidateRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get Candidate By ID (Admin)
    @Override
    public CandidateResponseDto getCandidateById(Long id) {
        CandidateEntity candidate = candidateRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Candidate", id));
        return mapToResponse(candidate);
    }

    //  Delete Candidate (Admin)
    @Override
    public void deleteCandidate(Long id) {
        CandidateEntity candidate = candidateRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Candidate", id));
        candidateRepository.delete(candidate);
    }

    //  Search by Name (Admin)
    @Override
    public List<CandidateResponseDto> searchByName(String name) {
        return candidateRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Search by Skill (Admin)
    @Override
    public List<CandidateResponseDto> searchBySkill(String skill) {
        return candidateRepository
                .findBySkillsContainingIgnoreCase(skill)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Search by City (Admin)
    @Override
    public List<CandidateResponseDto> searchByCity(String city) {
        return candidateRepository
                .findByCurrentCity(city)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ── Helper Methods ──────────────────────────────

    private void mapToEntity(
            CandidateRequestDto dto, CandidateEntity candidate) {
        candidate.setName(dto.getName());
        candidate.setEmail(dto.getEmail());
        candidate.setMobile(dto.getMobile());
        candidate.setQualification(dto.getQualification());
        candidate.setSkills(dto.getSkills());
        candidate.setExperience(dto.getExperience());
        candidate.setCurrentCity(dto.getCurrentCity());
        candidate.setResumeLink(dto.getResumeLink());
        candidate.setLinkedinProfile(dto.getLinkedinProfile());
        candidate.setGithubProfile(dto.getGithubProfile());
    }

    private CandidateResponseDto mapToResponse(
            CandidateEntity candidate) {
        CandidateResponseDto dto = new CandidateResponseDto();
        dto.setId(candidate.getId());
        dto.setName(candidate.getName());
        dto.setEmail(candidate.getEmail());
        dto.setMobile(candidate.getMobile());
        dto.setQualification(candidate.getQualification());
        dto.setSkills(candidate.getSkills());
        dto.setExperience(candidate.getExperience());
        dto.setCurrentCity(candidate.getCurrentCity());
        dto.setResumeLink(candidate.getResumeLink());
        dto.setLinkedinProfile(candidate.getLinkedinProfile());
        dto.setGithubProfile(candidate.getGithubProfile());
        dto.setRole(candidate.getRole());
        dto.setActive(candidate.isActive());
        dto.setCreatedAt(candidate.getCreatedAt());
        dto.setUpdatedAt(candidate.getUpdatedAt());
        return dto;
    }
}