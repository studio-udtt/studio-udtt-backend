package com.udtt.backend.stat.repository;

import com.udtt.backend.project.entity.Project;
import com.udtt.backend.project.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatProjectRepository extends JpaRepository<Project, Long> {

    long countByDeletedAtIsNull();

    long countByStatusAndDeletedAtIsNull(ProjectStatus status);

    List<Project> findByDeletedAtIsNullOrderByCreatedAtDesc();

    List<Project> findByIdAndDeletedAtIsNull(Long projectId);

    List<Project> findByStatusAndDeletedAtIsNullOrderByCreatedAtDesc(ProjectStatus status);
}