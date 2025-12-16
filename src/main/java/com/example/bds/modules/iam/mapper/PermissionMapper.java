package com.example.bds.modules.iam.mapper;

import com.example.bds.modules.iam.dto.Permission.PermissionDTO;
import com.example.bds.modules.iam.dto.Permission.CreatePermissionDTO;
import com.example.bds.modules.iam.dto.Permission.UpdatePermissionDTO;
import com.example.bds.modules.iam.entity.Permission;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PermissionMapper {
    // Chuyển đổi từ Permission entity sang PermissionDTO
    public PermissionDTO toDTO(Permission permission) {
        if (permission == null) {
            return null;
        }

        PermissionDTO dto = new PermissionDTO();
        dto.setId(permission.getId());
        dto.setName(permission.getName());
        dto.setDescription(permission.getDescription());
        dto.setModule(permission.getModule());
        dto.setIsActive(permission.isActive());
        dto.setCreatedAt(permission.getCreatedAt());
        dto.setUpdatedAt(permission.getUpdatedAt());
        return dto;
    }

    // Chuyển đổi từ CreatePermissionDTO sang Permission entity
    public Permission toEntity(CreatePermissionDTO createPermissionDTO) {
        if (createPermissionDTO == null) {
            return null;
        }

        Permission permission = new Permission();
        permission.setName(createPermissionDTO.getName());
        permission.setDescription(createPermissionDTO.getDescription());
        permission.setModule(createPermissionDTO.getModule());
        permission.setActive(createPermissionDTO.getIsActive() != null ? createPermissionDTO.getIsActive() : true);
        permission.setCreatedAt(LocalDateTime.now());
        return permission;
    }

    // Chuyển đổi từ UpdatePermissionDTO sang Permission entity
    public Permission toEntity(UpdatePermissionDTO updatePermissionDTO) {
        if (updatePermissionDTO == null) {
            return null;
        }

        Permission permission = new Permission();
        permission.setId(updatePermissionDTO.getId());
        permission.setName(updatePermissionDTO.getName());
        permission.setDescription(updatePermissionDTO.getDescription());
        permission.setModule(updatePermissionDTO.getModule());
        permission.setUpdatedAt(LocalDateTime.now());
        return permission;
    }

    // Cập nhật Permission entity từ UpdatePermissionDTO
    public void updateEntityFromDTO(UpdatePermissionDTO updatePermissionDTO, Permission permission) {
        if (updatePermissionDTO == null || permission == null) {
            return;
        }
        permission.setName(updatePermissionDTO.getName());
        permission.setDescription(updatePermissionDTO.getDescription());
        permission.setModule(updatePermissionDTO.getModule());
        permission.setUpdatedAt(LocalDateTime.now());
    }
}
