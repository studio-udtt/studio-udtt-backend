package com.udtt.backend.project.repository;

import com.udtt.backend.project.entity.Project;
import com.udtt.backend.project.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
        SELECT p
        FROM Project p
        WHERE (:status IS NULL OR p.status = :status)
          AND (:regionSido IS NULL OR p.regionSido = :regionSido)
          AND (:regionSigungu IS NULL OR p.regionSigungu = :regionSigungu)
        ORDER BY p.createdAt DESC
    """)
    List<Project> findProjects(
            @Param("status") ProjectStatus status,
            @Param("regionSido") String regionSido,
            @Param("regionSigungu") String regionSigungu
    );
}