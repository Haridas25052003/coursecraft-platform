package com.vibrantminds.vibrantminds.candidate;

import com.vibrantminds.vibrantminds.candidate.dto.CandidateLoginDto;
import com.vibrantminds.vibrantminds.candidate.dto.CandidateRequestDto;
import com.vibrantminds.vibrantminds.candidate.dto.CandidateResponseDto;
import java.util.List;

public interface CandidateService {

    // Public
    CandidateResponseDto register(CandidateRequestDto dto);
    String login(CandidateLoginDto dto);

    // Candidate
    CandidateResponseDto getProfile(String email);
    CandidateResponseDto updateProfile(String email,
                                       CandidateRequestDto dto);

    // Admin
    List<CandidateResponseDto> getAllCandidates();
    CandidateResponseDto getCandidateById(Long id);
    void deleteCandidate(Long id);
    List<CandidateResponseDto> searchByName(String name);
    List<CandidateResponseDto> searchBySkill(String skill);
    List<CandidateResponseDto> searchByCity(String city);
}