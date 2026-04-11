package com.vibrantminds.vibrantminds.admin;

import com.vibrantminds.vibrantminds.admin.dto.AdminLoginDto;
import com.vibrantminds.vibrantminds.admin.dto.AdminRequestDto;
import com.vibrantminds.vibrantminds.admin.dto.AdminResponseDto;

import java.util.List;

public interface AdminService {

    AdminResponseDto createAdmin(AdminRequestDto dto);

    String loginAdmin(AdminLoginDto dto);

    AdminResponseDto getAdminById(Long id);

    List<AdminResponseDto> getAllAdmins();

    void deleteAdmin(Long id);
}
