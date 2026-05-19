package com.udtt.backend.project.repository;

import com.udtt.backend.project.entity.ProjectApplication;
import com.udtt.backend.project.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectApplicationRepository extends JpaRepository<ProjectApplication, Long> {

    int countByProject_IdAndStatus(Long projectId, ApplicationStatus status);

    Optional<ProjectApplication> findByIdAndDeletedAtIsNull(Long id);
}
