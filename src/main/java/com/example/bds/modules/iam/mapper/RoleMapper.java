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
    /*
    Chuyển Role entity sang RoleDTO, Hàm này nhận 1 đối tượng Role và trả về một đối tượng RoleDTO tương ứng
    Tham số truyền vào là entity Role cần chuyển đổi
    Trả về đối tượng RoleDTO đã được chuyển đổi từ entity Role
    */
    public RoleDTO toDTO(Role role) {
        if (role == null) {
            return null;
        }
        RoleDTO dto = new RoleDTO(); // RoleDTO là đối tượng cần chuyển đổi, dto là biến đại diện cho đối tượng đó, RoleDTO() là hàm khởi tạo đối tượng RoleDTO mới
        dto.setId(role.getId()); // setId là hàm set id mới cho dto, role.getId() là hàm lấy id từ entity role
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
