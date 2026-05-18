package com.udtt.backend.project.repository;

import com.udtt.backend.project.entity.ProjectApplication;
import com.udtt.backend.project.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectApplicationRepository extends JpaRepository<ProjectApplication, Long> {

    int countByProjectProjectIdAndStatus(Long projectId, ApplicationStatus status);
}