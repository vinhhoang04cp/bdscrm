package com.example.bds.modules.iam.mapper;

import com.example.bds.modules.iam.dto.UserRole.UpdateUserRoleDTO;
import com.example.bds.modules.iam.dto.UserRole.CreateUserRoleDTO;
import com.example.bds.modules.iam.dto.UserRole.UserRoleDTO;
import com.example.bds.modules.iam.entity.UserRole;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserRoleMapper {
    // Chuyển đổi từ UserRole entity sang UserRoleDTO
    public UserRoleDTO toUserRoleDTO(UserRole userRole) {
        if (userRole == null) {
            return null;
        }
        UserRoleDTO dto = new UserRoleDTO();
        dto.setId(userRole.getId());
        dto.setUserId(userRole.getUser().getId());
        dto.setUserName(userRole.getUser().getUsername());
        dto.setRoleId(userRole.getRole().getId());
        dto.setRoleName(userRole.getRole().getName());
        dto.setAssignedAt(userRole.getAssignedAt());
        dto.setAssignedBy(userRole.getAssignedBy());
        return dto;
    }

    // Chuyển đổi từ CreateUserRoleDTO sang UserRole entity
    public UserRole toUserRoleEntity(CreateUserRoleDTO createDTO) {
        if (createDTO == null) {
            return null;
        }
        UserRole userRole = new UserRole();
        // Giả sử rằng User và Role đã được thiết lập ở nơi khác
        userRole.setAssignedAt(LocalDateTime.now());
        userRole.setAssignedBy(createDTO.getAssignedBy());
        return userRole;
    }

    // Chuyển từ UpdateUserRoleDTO sang UserRole entity
    public UserRole toUserRoleEntity(UpdateUserRoleDTO updateDTO) {
        if (updateDTO == null) {
            return null;
        }
        UserRole userRole = new UserRole();
        // Giả sử rằng User và Role đã được thiết lập ở nơi khác
        // Cập nhật các trường cần thiết từ updateDTO nếu có
        return userRole;
    }

    // Chuyển từ UserRoleDTO sang UserRole entity
    public UserRole toUserRoleEntity(UserRoleDTO dto) {
        if (dto == null) {
            return null;
        }
        UserRole userRole = new UserRole();
        // Giả sử rằng User và Role đã được thiết lập ở nơi khác
        userRole.setAssignedAt(dto.getAssignedAt());
        userRole.setAssignedBy(dto.getAssignedBy());
        return userRole;
    }
}
