package com.example.bds.modules.iam.service;

import com.example.bds.modules.iam.entity.Role;
import com.example.bds.modules.iam.repository.RoleRepository;
import com.example.bds.modules.iam.dto.Role.RoleDTO;
import com.example.bds.modules.iam.dto.Role.CreateRoleDTO;
import com.example.bds.modules.iam.dto.Role.UpdateRoleDTO;
import com.example.bds.modules.iam.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    // Lấy tất cả vai trò
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Lấy vai trò theo ID
    public RoleDTO getRoleById(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        return roleMapper.toDTO(role);
    }

    // Lấy vai trò theo tên
    public RoleDTO getRoleByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        return roleMapper.toDTO(role);
    }

    // Lấy các vai trò theo trạng thái hoạt động
    public List<RoleDTO> getRolesByIsActive(Boolean isActive) {
        List<Role> roles = roleRepository.findByIsActive(isActive);
        return roles.stream()
                .map(roleMapper::toDTO)
                .collect(Collectors.toList());
    }

    /*
    Tạo vai trò mới, trả về 1 RoleDTO, tham số đầu vào là CreateRoleDTO được truyền từ client
     */
    public RoleDTO createRole(CreateRoleDTO createRoleDTO) {
        if(roleRepository.existsByName(createRoleDTO.getName())) {
            throw new IllegalArgumentException("Role name already exists");
        }
        Role role = roleMapper.toEntity(createRoleDTO); // Gọi đến hàm đổi sang entity trong RoleMapper, với tham số đầu vào là CreateRoleDTO truyên từ client
        Role savedRole = roleRepository.save(role); // Lưu entity vào database thông qua roleRepository
        return roleMapper.toDTO(savedRole); // Trả về RoleDTO bằng cách gọi hàm đổi sang DTO trong RoleMapper
    }

    // Cập nhật vai trò
    public RoleDTO updateRole(UpdateRoleDTO updateRoleDTO) {
        Role existingRole = roleRepository.findById(updateRoleDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        if(!existingRole.getName().equals(updateRoleDTO.getName()) &&
           roleRepository.existsByName(updateRoleDTO.getName())) {
            throw new IllegalArgumentException("Role name already exists");
        }

        Role roleToUpdate = roleMapper.toEntity(updateRoleDTO);
        Role updatedRole = roleRepository.save(roleToUpdate);
        return roleMapper.toDTO(updatedRole);
    }
}
