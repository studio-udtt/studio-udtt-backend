package com.udtt.backend.project.repository;

import com.udtt.backend.project.entity.Project;
import com.udtt.backend.project.enums.ProjectStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
        SELECT p
        FROM Project p
        WHERE p.deletedAt IS NULL
          AND (:status IS NULL OR p.status = :status)
          AND (:regionSido IS NULL OR p.regionSido = :regionSido)
          AND (:regionSigungu IS NULL OR p.regionSigungu = :regionSigungu)
        ORDER BY p.createdAt DESC
    """)
    List<Project> findProjects(
            @Param("status") ProjectStatus status,
            @Param("regionSido") String regionSido,
            @Param("regionSigungu") String regionSigungu
    );

    @Query("SELECT p FROM Project p " +
           "WHERE p.deletedAt IS NULL " +
           "AND (:status IS NULL OR p.status = :status) " +
           "AND (:regionSido IS NULL OR p.regionSido = :regionSido) " +
           "ORDER BY p.createdAt DESC")
    Page<Project> findAllByCondition(
            @Param("status") String status,
            @Param("regionSido") String regionSido,
            Pageable pageable);

    Optional<Project> findByIdAndDeletedAtIsNull(Long projectId);
}
