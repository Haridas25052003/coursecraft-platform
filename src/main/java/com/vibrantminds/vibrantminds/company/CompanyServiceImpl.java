package com.vibrantminds.vibrantminds.company;

import com.vibrantminds.vibrantminds.company.dto.CompanyRequestDto;
import com.vibrantminds.vibrantminds.company.dto.CompanyResponseDto;
import com.vibrantminds.vibrantminds.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    //  Create Company (Admin)
    @Override
    public CompanyResponseDto createCompany(CompanyRequestDto dto) {

        if (companyRepository.existsByName(dto.getName())) {
            throw new RuntimeException(
                    "Company already exists with name : "
                            + dto.getName());
        }

        CompanyEntity company = new CompanyEntity();
        mapToEntity(dto, company);
        CompanyEntity saved = companyRepository.save(company);
        return mapToResponse(saved);
    }

    //  Update Company (Admin)
    @Override
    public CompanyResponseDto updateCompany(
            Long id, CompanyRequestDto dto) {

        CompanyEntity company = companyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company", id));

        mapToEntity(dto, company);
        CompanyEntity updated = companyRepository.save(company);
        return mapToResponse(updated);
    }

    //  Delete Company (Admin)
    @Override
    public void deleteCompany(Long id) {

        CompanyEntity company = companyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company", id));

        companyRepository.delete(company);
    }

    //  Toggle Active/Inactive (Admin)
    @Override
    public CompanyResponseDto toggleCompanyStatus(Long id) {

        CompanyEntity company = companyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company", id));

        company.setActive(!company.isActive());
        CompanyEntity updated = companyRepository.save(company);
        return mapToResponse(updated);
    }

    //  Get All Active Companies (Public)
    @Override
    public List<CompanyResponseDto> getAllActiveCompanies() {
        return companyRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get All Companies (Admin)
    @Override
    public List<CompanyResponseDto> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Get Company By ID
    @Override
    public CompanyResponseDto getCompanyById(Long id) {
        CompanyEntity company = companyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company", id));
        return mapToResponse(company);
    }

    //  Search by Name
    @Override
    public List<CompanyResponseDto> searchByName(String name) {
        return companyRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Search by City
    @Override
    public List<CompanyResponseDto> searchByCity(String city) {
        return companyRepository.findByCity(city)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Search by State
    @Override
    public List<CompanyResponseDto> searchByState(String state) {
        return companyRepository.findByState(state)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Search by Industry
    @Override
    public List<CompanyResponseDto> searchByIndustry(
            String industry) {
        return companyRepository.findByIndustry(industry)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Search by Company Size
    @Override
    public List<CompanyResponseDto> searchByCompanySize(
            String companySize) {
        return companyRepository.findByCompanySize(companySize)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ── Helper Methods ──────────────────────────────

    private void mapToEntity(
            CompanyRequestDto dto, CompanyEntity company) {
        company.setName(dto.getName());
        company.setIndustry(dto.getIndustry());
        company.setCity(dto.getCity());
        company.setState(dto.getState());
        company.setAddress(dto.getAddress());
        company.setContactPerson(dto.getContactPerson());
        company.setContactEmail(dto.getContactEmail());
        company.setContactPhone(dto.getContactPhone());
        company.setWebsite(dto.getWebsite());
        company.setLogo(dto.getLogo());
        company.setDescription(dto.getDescription());
        company.setCompanySize(dto.getCompanySize());
        company.setFoundedYear(dto.getFoundedYear());
        company.setActive(dto.isActive());
    }

    private CompanyResponseDto mapToResponse(
            CompanyEntity company) {
        CompanyResponseDto dto = new CompanyResponseDto();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setIndustry(company.getIndustry());
        dto.setCity(company.getCity());
        dto.setState(company.getState());
        dto.setAddress(company.getAddress());
        dto.setContactPerson(company.getContactPerson());
        dto.setContactEmail(company.getContactEmail());
        dto.setContactPhone(company.getContactPhone());
        dto.setWebsite(company.getWebsite());
        dto.setLogo(company.getLogo());
        dto.setDescription(company.getDescription());
        dto.setCompanySize(company.getCompanySize());
        dto.setFoundedYear(company.getFoundedYear());
        dto.setActive(company.isActive());
        dto.setCreatedAt(company.getCreatedAt());
        dto.setUpdatedAt(company.getUpdatedAt());
        return dto;
    }
}