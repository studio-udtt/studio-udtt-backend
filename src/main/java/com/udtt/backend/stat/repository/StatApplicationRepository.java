package com.udtt.backend.stat.repository;

import com.udtt.backend.project.entity.ProjectApplication;
import com.udtt.backend.project.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatApplicationRepository extends JpaRepository<ProjectApplication, Long> {

    long countByDeletedAtIsNull();

    long countByStatusAndDeletedAtIsNull(ApplicationStatus status);

    long countByProjectIdAndDeletedAtIsNull(Long projectId);

    long countByProjectIdAndStatusAndDeletedAtIsNull(
            Long projectId,
            ApplicationStatus status
    );
}