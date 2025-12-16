package com.example.bds.modules.iam.service;

import com.example.bds.modules.iam.entity.Role;
import com.example.bds.modules.iam.entity.Permission;
import com.example.bds.modules.iam.entity.RolePermission;
import com.example.bds.modules.iam.repository.RolePermissionRepository;
import com.example.bds.modules.iam.repository.RoleRepository;
import com.example.bds.modules.iam.repository.PermissionRepository;
import com.example.bds.modules.iam.dto.RolePermission.RolePermissionDTO;
import com.example.bds.modules.iam.dto.RolePermission.CreateRolePermissionDTO;
import com.example.bds.modules.iam.dto.RolePermission.AssignPermissionsToRoleDTO;
import com.example.bds.modules.iam.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;
    private final RolePermissionMapper rolePermissionMapper;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RolePermissionService(RolePermissionRepository rolePermissionRepository,
                                 RolePermissionMapper rolePermissionMapper,
                                 RoleRepository roleRepository,
                                 PermissionRepository permissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
        this.rolePermissionMapper = rolePermissionMapper;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    // Các phương thức dịch vụ để quản lý RolePermission

    // Lấy tất cả RolePermissionDTO theo roleId
    public List<RolePermissionDTO> getRolePermissionsByRoleId(Long roleId) {
        List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(roleId);
        return rolePermissions.stream()
                .map(rolePermissionMapper::toRolePermissionDTO)
                .collect(Collectors.toList());
    }

    // Thêm RolePermission từ CreateRolePermissionDTO
    public RolePermissionDTO addRolePermission(CreateRolePermissionDTO createDTO) {
        // Fetch Role và Permission từ database
        Role role = roleRepository.findById(createDTO.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + createDTO.getRoleId()));

        Permission permission = permissionRepository.findById(createDTO.getPermissionId())
                .orElseThrow(() -> new IllegalArgumentException("Permission not found with id: " + createDTO.getPermissionId()));

        // Tạo RolePermission entity và set các giá trị
        RolePermission rolePermission = rolePermissionMapper.toRolePermissionEntity(createDTO);
        rolePermission.setRole(role);
        rolePermission.setPermission(permission);

        RolePermission savedRolePermission = rolePermissionRepository.save(rolePermission);
        return rolePermissionMapper.toRolePermissionDTO(savedRolePermission);
    }

    // Gán nhiều quyền cho một vai trò từ AssignPermissionsToRoleDTO
    public List<RolePermissionDTO> assignPermissionsToRole(AssignPermissionsToRoleDTO assignDTO) {
        // Fetch Role từ database
        Role role = roleRepository.findById(assignDTO.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + assignDTO.getRoleId()));

        // Tạo danh sách RolePermission
        List<RolePermission> rolePermissions = assignDTO.getPermissionIds().stream()
                .map(permissionId -> {
                    // Fetch Permission từ database
                    Permission permission = permissionRepository.findById(permissionId)
                            .orElseThrow(() -> new IllegalArgumentException("Permission not found with id: " + permissionId));

                    // Tạo RolePermission entity và set các giá trị
                    RolePermission rolePermission = rolePermissionMapper.toRolePermissionEntity(assignDTO);
                    rolePermission.setRole(role);
                    rolePermission.setPermission(permission);
                    return rolePermission;
                }).collect(Collectors.toList());

        List<RolePermission> savedRolePermissions = rolePermissionRepository.saveAll(rolePermissions);
        return savedRolePermissions.stream()
                .map(rolePermissionMapper::toRolePermissionDTO)
                .collect(Collectors.toList());
    }

    // Xóa RolePermission theo roleId và permissionId
    public void deleteRolePermission(Long roleId, Long permissionId) {
        if(rolePermissionRepository.findByRoleIdAndPermissionId(roleId, permissionId).isPresent()) {
            rolePermissionRepository.deleteByRoleIdAndPermissionId(roleId, permissionId);
        }
    }
}
