package com.udtt.backend.project.repository;

import com.udtt.backend.project.entity.ProjectRequest;
import com.udtt.backend.project.enums.ProjectRequestStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRequestRepository extends JpaRepository<ProjectRequest, Long> {

    Optional<ProjectRequest> findByIdAndDeletedAtIsNull(Long id);

    @Query("SELECT r FROM ProjectRequest r " +
           "WHERE r.deletedAt IS NULL " +
           "AND (:status IS NULL OR r.status = :status) " +
           "ORDER BY r.createdAt DESC")
    Page<ProjectRequest> findAllByStatusAndNotDeleted(
            @Param("status") ProjectRequestStatus status,
            Pageable pageable);
}
