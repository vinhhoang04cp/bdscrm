package com.example.bds.modules.iam.service;

import com.example.bds.modules.iam.entity.Role;
import com.example.bds.modules.iam.entity.User;
import com.example.bds.modules.iam.entity.UserRole;
import com.example.bds.modules.iam.repository.UserRoleRepository;
import com.example.bds.modules.iam.repository.RoleRepository;
import com.example.bds.modules.iam.repository.UserRoleRepository;
import com.example.bds.modules.iam.dto.UserRole.UserRoleDTO;
import com.example.bds.modules.iam.dto.UserRole.CreateUserRoleDTO;
import com.example.bds.modules.iam.dto.UserRole.UpdateUserRoleDTO;
import com.example.bds.modules.iam.mapper.UserRoleMapper;
import com.example.bds.modules.iam.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRoleMapper userRoleMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public UserRoleService(UserRoleRepository userRoleRepository,
                           UserRoleMapper userRoleMapper,
                           RoleRepository roleRepository,
                           UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRoleMapper = userRoleMapper;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    // Các phương thức dịch vụ để quản lý UserRole

    // Lấy tất cả UserRoleDTO theo userId
    public List<UserRoleDTO> getUserRolesByUserId(Long userId) {
        List<UserRole> userRoles = userRoleRepository.findByUserId(userId);
        return userRoles.stream()
                .map(userRoleMapper::toUserRoleDTO)
                .collect(Collectors.toList());
    }

    // Thêm UserRole từ CreateUserRoleDTO
    public UserRoleDTO addUserRole(CreateUserRoleDTO createDTO) {
        // Fetch User và Role từ database
        User user = userRepository.findById(createDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + createDTO.getUserId()));
        Role role = roleRepository.findById(createDTO.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + createDTO.getRoleId()));
        // Tạo UserRole entity và set các giá trị
        UserRole userRole = userRoleMapper.toUserRoleEntity(createDTO);
        userRole.setUser(user);
        userRole.setRole(role);
        UserRole savedUserRole = userRoleRepository.save(userRole);
        return userRoleMapper.toUserRoleDTO(savedUserRole);
    }

    // Cập nhật UserRole từ UpdateUserRoleDTO
    public UserRoleDTO updateUserRole(Long id, UpdateUserRoleDTO updateDTO) {
        UserRole existingUserRole = userRoleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserRole not found with id: " + id));
        // Cập nhật các trường cần thiết từ updateDTO
        UserRole updatedUserRole = userRoleMapper.toUserRoleEntity(updateDTO);
        updatedUserRole.setId(existingUserRole.getId());
        updatedUserRole.setUser(existingUserRole.getUser());
        updatedUserRole.setRole(existingUserRole.getRole());
        UserRole savedUserRole = userRoleRepository.save(updatedUserRole);
        return userRoleMapper.toUserRoleDTO(savedUserRole);
    }

    // Xóa UserRole theo id
    public void deleteUserRole(Long id) {
        if (!userRoleRepository.existsById(id)) {
            throw new IllegalArgumentException("UserRole not found with id: " + id);
        }
        userRoleRepository.deleteById(id);
    }
}
