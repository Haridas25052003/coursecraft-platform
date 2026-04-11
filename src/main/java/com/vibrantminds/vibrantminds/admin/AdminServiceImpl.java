package com.vibrantminds.vibrantminds.admin;

import com.vibrantminds.vibrantminds.admin.dto.AdminLoginDto;
import com.vibrantminds.vibrantminds.admin.dto.AdminRequestDto;
import com.vibrantminds.vibrantminds.admin.dto.AdminResponseDto;
import com.vibrantminds.vibrantminds.exception.ResourceNotFoundException;
import com.vibrantminds.vibrantminds.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AdminResponseDto createAdmin(AdminRequestDto dto) {

        if (adminRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Admin already exists with this email");
        }

        AdminEntity admin = new AdminEntity();
        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setRole("ROLE_ADMIN");

        AdminEntity saved = adminRepository.save(admin);
        return mapToResponse(saved);
    }


    @Override
    public String loginAdmin(AdminLoginDto dto) {

        AdminEntity admin = adminRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.getPassword(), admin.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        // We will return JWT token here in Step 4
        // return jwtUtil.generateToken(admin.getEmail(), admin.getRole());
        return "Login successful - JWT will be added in Step 4";
    }

    @Override
    public AdminResponseDto getAdminById(Long id) {

        AdminEntity admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin", id));

        return mapToResponse(admin);
    }

    @Override
    public List<AdminResponseDto> getAllAdmins() {

        return adminRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAdmin(Long id) {

        AdminEntity admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin", id));

        adminRepository.delete(admin);
    }

    //helper method
    private AdminResponseDto mapToResponse(AdminEntity admin) {
        AdminResponseDto dto = new AdminResponseDto();
        dto.setId(admin.getId());
        dto.setName(admin.getName());
        dto.setEmail(admin.getEmail());
        dto.setRole(admin.getRole());
        dto.setCreatedAt(admin.getCreatedAt());
        return dto;
    }
}
