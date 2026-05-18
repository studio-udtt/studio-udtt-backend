package com.udtt.backend.admin.repository;

import com.udtt.backend.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {

    Optional<Admin> findByLoginIdAndDeletedAtIsNull(String loginId);

    Optional<Admin> findByIdAndDeletedAtIsNull(UUID id);
}
