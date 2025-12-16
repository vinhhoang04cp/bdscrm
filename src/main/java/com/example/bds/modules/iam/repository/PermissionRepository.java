package com.example.bds.modules.iam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bds.modules.iam.entity.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
    List<Permission> findByIsActive(Boolean isActive);
    Optional<Permission> findByModule(String module);

    boolean existsByName(String name);
}
