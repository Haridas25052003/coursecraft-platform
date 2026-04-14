package com.vibrantminds.vibrantminds.company;

import com.vibrantminds.vibrantminds.company.dto.CompanyRequestDto;
import com.vibrantminds.vibrantminds.company.dto.CompanyResponseDto;
import com.vibrantminds.vibrantminds.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    //  PUBLIC - Get all active companies
    // GET /api/companies
    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyResponseDto>>>
            getAllActiveCompanies() {
        List<CompanyResponseDto> companies =
                companyService.getAllActiveCompanies();
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Companies fetched successfully",
                        companies));
    }

    //  PUBLIC - Get company by id
    // GET /api/companies/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponseDto>>
            getCompanyById(@PathVariable Long id) {
        CompanyResponseDto company =
                companyService.getCompanyById(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Company fetched successfully", company));
    }

    //  PUBLIC - Search companies
    // GET /api/companies/search?name=TCS
    // GET /api/companies/search?city=Pune
    // GET /api/companies/search?industry=IT
    // GET /api/companies/search?companySize=MNC
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CompanyResponseDto>>>
            searchCompanies(
                @RequestParam(required = false) String name,
                @RequestParam(required = false) String city,
                @RequestParam(required = false) String state,
                @RequestParam(required = false) String industry,
                @RequestParam(required = false) String companySize) {

        List<CompanyResponseDto> companies;

        if (name != null) {
            companies = companyService.searchByName(name);
        } else if (city != null) {
            companies = companyService.searchByCity(city);
        } else if (state != null) {
            companies = companyService.searchByState(state);
        } else if (industry != null) {
            companies = companyService.searchByIndustry(industry);
        } else if (companySize != null) {
            companies = companyService.searchByCompanySize(
                    companySize);
        } else {
            companies = companyService.getAllActiveCompanies();
        }

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Companies fetched successfully",
                        companies));
    }

    //  ADMIN - Get all companies including inactive
    // GET /api/companies/admin/all
    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponse<List<CompanyResponseDto>>>
            getAllCompanies() {
        List<CompanyResponseDto> companies =
                companyService.getAllCompanies();
        return ResponseEntity.ok(
                ApiResponse.success(
                        "All companies fetched successfully",
                        companies));
    }

    //  ADMIN - Create company
    // POST /api/companies/admin
    @PostMapping("/admin")
    public ResponseEntity<ApiResponse<CompanyResponseDto>>
            createCompany(
                @Valid @RequestBody CompanyRequestDto dto) {
        CompanyResponseDto company =
                companyService.createCompany(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Company created successfully", company));
    }

    //  ADMIN - Update company
    // PUT /api/companies/admin/{id}
    @PutMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<CompanyResponseDto>>
            updateCompany(
                @PathVariable Long id,
                @Valid @RequestBody CompanyRequestDto dto) {
        CompanyResponseDto company =
                companyService.updateCompany(id, dto);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Company updated successfully", company));
    }

    //  ADMIN - Toggle company status
    // PATCH /api/companies/admin/{id}/toggle
    @PatchMapping("/admin/{id}/toggle")
    public ResponseEntity<ApiResponse<CompanyResponseDto>>
            toggleCompanyStatus(@PathVariable Long id) {
        CompanyResponseDto company =
                companyService.toggleCompanyStatus(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Company status updated successfully",
                        company));
    }

    //  ADMIN - Delete company
    // DELETE /api/companies/admin/{id}
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<?>>
            deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Company deleted successfully"));
    }
}