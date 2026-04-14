package com.vibrantminds.vibrantminds.company;

import com.vibrantminds.vibrantminds.company.dto.CompanyRequestDto;
import com.vibrantminds.vibrantminds.company.dto.CompanyResponseDto;
import java.util.List;

public interface CompanyService {

    // Admin operations
    CompanyResponseDto createCompany(CompanyRequestDto dto);
    CompanyResponseDto updateCompany(Long id, CompanyRequestDto dto);
    void deleteCompany(Long id);
    CompanyResponseDto toggleCompanyStatus(Long id);

    // Public operations
    List<CompanyResponseDto> getAllActiveCompanies();
    List<CompanyResponseDto> getAllCompanies();
    CompanyResponseDto getCompanyById(Long id);

    // Search operations
    List<CompanyResponseDto> searchByName(String name);
    List<CompanyResponseDto> searchByCity(String city);
    List<CompanyResponseDto> searchByState(String state);
    List<CompanyResponseDto> searchByIndustry(String industry);
    List<CompanyResponseDto> searchByCompanySize(String companySize);
}