package com.example.bds.modules.iam.mapper;

import com.example.bds.modules.iam.dto.User.CreateUserDTO;
import com.example.bds.modules.iam.dto.User.UserDTO;
import com.example.bds.modules.iam.dto.User.UpdateUserDTO;
import com.example.bds.modules.iam.entity.User;
import com.example.bds.modules.iam.dto.User.UserDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {
    // Chuyển đổi từ Team entity sang TeamDTO
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        // Khởi tạo đối tượng TeamDTO và thiết lập các thuộc tính
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setPhone(user.getPhone());
        dto.setFullName(user.getFullName());
        dto.setStatus(user.getStatus().name());
        dto.setAddress(user.getAddress());
        dto.setTeamId(user.getTeam() != null ? user.getTeam().getId() : null);
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    // Chuyển đổi từ CreateUserDTO sang User entity
    public User toEntity(CreateUserDTO createUserDTO) {
        if (createUserDTO == null) {
            return null;
        }

        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setEmail(createUserDTO.getEmail());
        user.setFullName(createUserDTO.getFullName());
        user.setPhone(createUserDTO.getPhone());
        user.setAddress(createUserDTO.getAddress());
        user.setPassword(createUserDTO.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        user.setTeam(null); // Team sẽ được thiết lập sau trong Service
        return user;
    }

    // Chuyển đổi từ UpdateUserDTO sang User entity
    public User toEntity(UpdateUserDTO updateUserDTO) {
        if (updateUserDTO == null) {
            return null;
        }

        User user = new User();
        user.setEmail(updateUserDTO.getEmail());
        user.setFullName(updateUserDTO.getFullName());
        user.setPhone(updateUserDTO.getPhone());
        user.setAddress(updateUserDTO.getAddress());
        user.setPassword(updateUserDTO.getPassword());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    // Cập nhật User entity từ UpdateUserDTO
    public void updateEntity(User user, UpdateUserDTO updateUserDTO) {
        if (user == null || updateUserDTO == null) {
            return;
        }

        if (updateUserDTO.getEmail() != null) {
            user.setEmail(updateUserDTO.getEmail());
        }
        if (updateUserDTO.getFullName() != null) {
            user.setFullName(updateUserDTO.getFullName());
        }
        if (updateUserDTO.getPhone() != null) {
            user.setPhone(updateUserDTO.getPhone());
        }
        if (updateUserDTO.getAddress() != null) {
            user.setAddress(updateUserDTO.getAddress());
        }
        if (updateUserDTO.getPassword() != null) {
            user.setPassword(updateUserDTO.getPassword());
        }
        user.setUpdatedAt(LocalDateTime.now());
    }
}
