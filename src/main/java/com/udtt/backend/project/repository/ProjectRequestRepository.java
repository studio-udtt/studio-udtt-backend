package com.udtt.backend.project.repository;

import com.udtt.backend.project.entity.ProjectRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRequestRepository extends JpaRepository<ProjectRequest, Long> {

    Optional<ProjectRequest> findByIdAndDeletedAtIsNull(Long id);
}