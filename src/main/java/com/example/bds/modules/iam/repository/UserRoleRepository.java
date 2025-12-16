package com.example.bds.modules.iam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bds.modules.iam.entity.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUserId(Long userId);
    Optional<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);

    void deleteByUserIdAndRoleId(Long userId, Long roleId);
}
