package com.udtt.backend.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.udtt.backend.admin.dto.AdminProjectDetailDto;
import com.udtt.backend.admin.dto.AdminProjectListDto;
import com.udtt.backend.admin.dto.UpdateProjectDto;
import com.udtt.backend.admin.dto.UpdateProjectStatusDto;

public interface AdminProjectService {

    Page<AdminProjectListDto> getProjectList(String status, String regionSido, Pageable pageable);
    AdminProjectDetailDto getProjectDetail(Long projectId);
    void updateProject(Long projectId, UpdateProjectDto dto);
    void updateProjectStatus(Long projectId, UpdateProjectStatusDto dto);
    void deleteProject(Long projectId);
}
