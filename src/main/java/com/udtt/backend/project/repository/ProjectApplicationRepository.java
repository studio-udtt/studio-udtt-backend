package com.udtt.backend.project.repository;

import com.udtt.backend.project.entity.ProjectApplication;
import com.udtt.backend.project.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectApplicationRepository extends JpaRepository<ProjectApplication, Long> {

    int countByProjectIdAndStatus(Long projectId, ApplicationStatus status);
}