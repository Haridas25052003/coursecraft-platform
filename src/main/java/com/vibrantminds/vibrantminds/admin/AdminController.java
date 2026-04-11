package com.vibrantminds.vibrantminds.admin;

import com.vibrantminds.vibrantminds.admin.*;
import com.vibrantminds.vibrantminds.admin.dto.AdminLoginDto;
import com.vibrantminds.vibrantminds.admin.dto.AdminRequestDto;
import com.vibrantminds.vibrantminds.admin.dto.AdminResponseDto;
import com.vibrantminds.vibrantminds.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // POST /api/admin/register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AdminResponseDto>> createAdmin(
            @Valid @RequestBody AdminRequestDto dto) {

        AdminResponseDto response = adminService.createAdmin(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Admin created successfully", response));
    }

    //POST /api/admin/login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginAdmin(
            @Valid @RequestBody AdminLoginDto dto) {

        String token = adminService.loginAdmin(dto);
        return ResponseEntity
                .ok(ApiResponse.success("Login successful", token));
    }

    // GET /api/admin/all
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AdminResponseDto>>> getAllAdmins() {

        List<AdminResponseDto> admins = adminService.getAllAdmins();
        return ResponseEntity
                .ok(ApiResponse.success("Admins fetched successfully", admins));
    }

    // GET /api/admin/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AdminResponseDto>> getAdminById(
            @PathVariable Long id) {

        AdminResponseDto admin = adminService.getAdminById(id);
        return ResponseEntity
                .ok(ApiResponse.success("Admin fetched successfully", admin));
    }

    // DELETE /api/admin/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteAdmin(
            @PathVariable Long id) {

        adminService.deleteAdmin(id);
        return ResponseEntity
                .ok(ApiResponse.success("Admin deleted successfully"));
    }
}
