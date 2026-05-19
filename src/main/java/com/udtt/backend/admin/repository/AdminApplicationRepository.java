package com.udtt.backend.admin.repository;

import com.udtt.backend.project.entity.ProjectApplication;
import com.udtt.backend.project.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminApplicationRepository extends JpaRepository<ProjectApplication, Long> {

    Page<ProjectApplication> findByDeletedAtIsNullOrderByCreatedAtDesc(Pageable pageable);

    Page<ProjectApplication> findByProjectIdAndDeletedAtIsNullOrderByCreatedAtDesc(
            Long projectId,
            Pageable pageable
    );

    Page<ProjectApplication> findByStatusAndDeletedAtIsNullOrderByCreatedAtDesc(
            ApplicationStatus status,
            Pageable pageable
    );

    Page<ProjectApplication> findByProjectIdAndStatusAndDeletedAtIsNullOrderByCreatedAtDesc(
            Long projectId,
            ApplicationStatus status,
            Pageable pageable
    );

    Optional<ProjectApplication> findByIdAndDeletedAtIsNull(Long id);

    long countByProjectIdAndStatusAndDeletedAtIsNull(
            Long projectId,
            ApplicationStatus status
    );
}