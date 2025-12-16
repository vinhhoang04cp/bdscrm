package com.example.bds.modules.iam.service;
import com.example.bds.modules.iam.entity.Permission;
import com.example.bds.modules.iam.repository.PermissionRepository;
import com.example.bds.modules.iam.dto.Permission.PermissionDTO;
import com.example.bds.modules.iam.dto.Permission.CreatePermissionDTO;
import com.example.bds.modules.iam.dto.Permission.UpdatePermissionDTO;
import com.example.bds.modules.iam.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public PermissionService(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    // Các phương thức dịch vụ sẽ được triển khai ở đây

    // Lấy tất cả các Permission
    public List<PermissionDTO> findAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(permissionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Lấy Permission theo ID
    public PermissionDTO findPermissionById(Long permissionId) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found"));
        return permissionMapper.toDTO(permission);
    }

    // Lấy Permission theo tên
    public PermissionDTO findPermissionByName(String name) {
        Permission permission = permissionRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found"));
        return permissionMapper.toDTO(permission);
    }

    // Lấy Permission theo module
    public PermissionDTO findPermissionByModule(String module) {
        Permission permission = permissionRepository.findByModule(module)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found"));
        return permissionMapper.toDTO(permission);
    }

    // Lấy các Permission theo trạng thái hoạt động
    public List<PermissionDTO> findPermissionsByIsActive(Boolean isActive) {
        List<Permission> permissions = permissionRepository.findByIsActive(isActive);
        return permissions.stream()
                .map(permissionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Các phương thức tạo, cập nhật, xóa Permission

    // Tạo mới một Permission
    public PermissionDTO createPermission(CreatePermissionDTO createPermissionDTO) {
        if(permissionRepository.existsByName(createPermissionDTO.getName())) {
            throw new IllegalArgumentException("Permission name already exists");
        }
        Permission permission = permissionMapper.toEntity(createPermissionDTO);
        Permission savedPermission = permissionRepository.save(permission);
        return permissionMapper.toDTO(savedPermission);
    }

    // Cập nhật một Permission
    public PermissionDTO updatePermission(UpdatePermissionDTO updatePermissionDTO) {
        Permission existingPermission = permissionRepository.findById(updatePermissionDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Permission not found"));

        // Kiểm tra name nếu có thay đổi
        if(updatePermissionDTO.getName() != null &&
           !existingPermission.getName().equals(updatePermissionDTO.getName()) &&
           permissionRepository.existsByName(updatePermissionDTO.getName())) {
            throw new IllegalArgumentException("Permission name already exists");
        }

        // Cập nhật các trường
        if(updatePermissionDTO.getName() != null) {
            existingPermission.setName(updatePermissionDTO.getName());
        }
        if(updatePermissionDTO.getDescription() != null) {
            existingPermission.setDescription(updatePermissionDTO.getDescription());
        }
        if(updatePermissionDTO.getModule() != null) {
            existingPermission.setModule(updatePermissionDTO.getModule());
        }
        if(updatePermissionDTO.getIsActive() != null) {
            existingPermission.setActive(updatePermissionDTO.getIsActive());
        }

        Permission updatedPermission = permissionRepository.save(existingPermission);
        return permissionMapper.toDTO(updatedPermission);
    }

    // Xóa một Permission theo ID
    public void deletePermission(Long permissionId) {
        if (permissionRepository.findById(permissionId).isEmpty()) {
            throw new IllegalArgumentException("Permission not found");
        }
        permissionRepository.deleteById(permissionId);
    }
}
