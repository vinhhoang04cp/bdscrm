package com.example.bds.modules.iam.mapper;

import com.example.bds.modules.iam.dto.RolePermission.RolePermissionDTO;
import com.example.bds.modules.iam.dto.RolePermission.CreateRolePermissionDTO;
import com.example.bds.modules.iam.dto.RolePermission.AssignPermissionsToRoleDTO;
import com.example.bds.modules.iam.entity.RolePermission;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RolePermissionMapper {
    // Chuyển đổi từ RolePermission entity sang RolePermissionDTO
    public  RolePermissionDTO toRolePermissionDTO(RolePermission rolePermission)
    {
        if (rolePermission == null) {
            return null;
        }
        RolePermissionDTO dto = new RolePermissionDTO();
        dto.setId(rolePermission.getId());
        dto.setRoleId(rolePermission.getRole().getId());
        dto.setRoleName(rolePermission.getRole().getName());
        dto.setPermissionId(rolePermission.getPermission().getId());
        dto.setPermissionName(rolePermission.getPermission().getName());
        dto.setPermissionModule(getClass().getSimpleName());
        dto.setGrantedAt(rolePermission.getGrantedAt());
        dto.setGrantedBy(rolePermission.getGrantedBy());
        return dto;
    }

    // Chuyển đổi từ CreateRolePermissionDTO sang RolePermission entity
    public RolePermission toRolePermissionEntity(CreateRolePermissionDTO createDTO){
        if(createDTO == null){
            return null;
        }
        RolePermission rolePermission = new RolePermission();
        // Giả sử rằng Role và Permission đã được thiết lập ở nơi khác
        rolePermission.setGrantedAt(LocalDateTime.now());
        rolePermission.setGrantedBy(createDTO.getGrantedBy());
        return rolePermission;
    }

    // Chuyển đổi từ AssignPermissionsToRoleDTO sang RolePermission entity
    public RolePermission toRolePermissionEntity(AssignPermissionsToRoleDTO assignDTO){
        if(assignDTO == null) {
            return null;
        }
        RolePermission rolePermission = new RolePermission();
        // Giả sử rằng Role và Permission đã được thiết lập ở nơi khác
        rolePermission.setGrantedAt(LocalDateTime.now());
        rolePermission.setGrantedBy(assignDTO.getGrantedBy());
        return rolePermission;
    }

    // Chuyển từ RolePermissionDTO sang RolePermission entity
    public RolePermission toRolePermissionEntity(RolePermissionDTO dto) {
        if (dto == null) {
            return null;
        }
        RolePermission rolePermission = new RolePermission();
        rolePermission.setId(dto.getId());
        // Giả sử rằng Role và Permission đã được thiết lập ở nơi khác
        rolePermission.setGrantedAt(dto.getGrantedAt());
        rolePermission.setGrantedBy(dto.getGrantedBy());
        return rolePermission;
    }
}
