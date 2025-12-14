package com.example.bds.modules.iam.mapper;

import com.example.bds.modules.iam.dto.Role.RoleDTO;
import com.example.bds.modules.iam.dto.Role.CreateRoleDTO;
import com.example.bds.modules.iam.dto.Role.UpdateRoleDTO;
import com.example.bds.modules.iam.entity.Role;
import com.example.bds.modules.iam.entity.TeamStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RoleMapper {
    // Chuyển Role entity sang RoleDTO
    public RoleDTO toDTO(Role role) {
        if (role == null) {
            return null;
        }
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        dto.setIsAdminRole(role.isAdminRole()); // hoặc role.getAdminRole()
        dto.setIsActive(role.isActive()); // hoặc role.getActive()
        dto.setCreatedAt(role.getCreatedAt());
        dto.setUpdatedAt(role.getUpdatedAt());
        return dto;
    }

    // Chuyển CreateRoleDTO sang Role entity
    public Role toEntity(CreateRoleDTO createRoleDTO) {
        if (createRoleDTO == null) {
            return null;
        }

        Role role = new Role();
        role.setName(createRoleDTO.getName());
        role.setDescription(createRoleDTO.getDescription());
        role.setAdminRole(createRoleDTO.getIsAdminRole());
        role.setActive(createRoleDTO.getIsActive());
        role.setCreatedAt(LocalDateTime.now());
        return role;
    }

    // Chuyển UpdateRoleDTO sang Role entity
    public Role toEntity(UpdateRoleDTO updateRoleDTO) {
        if (updateRoleDTO == null) {
            return null;
        }
        Role role = new Role();
        role.setId(updateRoleDTO.getId());
        role.setName(updateRoleDTO.getName());
        role.setDescription(updateRoleDTO.getDescription());
        role.setAdminRole(updateRoleDTO.getIsAdminRole());
        role.setActive(updateRoleDTO.getIsActive());
        role.setUpdatedAt(LocalDateTime.now());
        return role;
    }

    // Cập hật entity từ DTO

    public void updateEntity(UpdateRoleDTO updateRoleDTO, Role role) {
        if (updateRoleDTO == null || role == null) {
            return;
        }
        role.setName(updateRoleDTO.getName());
        role.setDescription(updateRoleDTO.getDescription());
        role.setAdminRole(updateRoleDTO.getIsAdminRole());
        role.setActive(updateRoleDTO.getIsActive());
        role.setUpdatedAt(LocalDateTime.now());

    }
}
