package com.example.bds.modules.iam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bds.modules.iam.entity.RolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    List<RolePermission> findByRoleId(Long roleId);
    Optional<RolePermission> findByRoleIdAndPermissionId(Long roleId, Long permissionId);

    void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
