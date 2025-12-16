package com.example.bds.modules.iam.controller;

import com.example.bds.modules.iam.dto.RolePermission.RolePermissionDTO;
import com.example.bds.modules.iam.dto.RolePermission.CreateRolePermissionDTO;
import com.example.bds.modules.iam.dto.RolePermission.AssignPermissionsToRoleDTO;
import com.example.bds.modules.iam.service.RolePermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-permissions")
@Tag(name = "Role Permission Management", description = "APIs quản lý phân quyền role-permission trong hệ thống IAM")
public class RolePermissionController {
    // Khai báo dịch vụ RolePermissionService
    private final RolePermissionService rolePermissionService;
    public RolePermissionController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    // Các endpoint để quản lý RolePermission

    // Lấy tất cả RolePermissionDTO theo roleId
    @GetMapping("/by-role/{roleId}")
    public ResponseEntity<List<RolePermissionDTO>> getRolePermissionsByRoleId(@PathVariable Long roleId) {
        List<RolePermissionDTO> rolePermissions = rolePermissionService.getRolePermissionsByRoleId(roleId);
        return ResponseEntity.ok(rolePermissions);
    }

    // Thêm RolePermission từ CreateRolePermissionDTO
    @PostMapping
    public ResponseEntity<RolePermissionDTO> addRolePermission(@RequestBody CreateRolePermissionDTO createDTO) {
        try {
            RolePermissionDTO rolePermissionDTO = rolePermissionService.addRolePermission(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(rolePermissionDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Gán nhiều quyền cho một vai trò từ AssignPermissionsToRoleDTO
    @PostMapping("/assign")
    public ResponseEntity<List<RolePermissionDTO>> assignPermissionsToRole(@RequestBody AssignPermissionsToRoleDTO assignDTO) {
        try {
            List<RolePermissionDTO> rolePermissionDTOs = rolePermissionService.assignPermissionsToRole(assignDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(rolePermissionDTOs);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Xóa RolePermission theo roleId và permissionId
    @DeleteMapping
    public ResponseEntity<Void> deleteRolePermission(@RequestParam Long roleId, @RequestParam Long permissionId) {
        try {
            rolePermissionService.deleteRolePermission(roleId, permissionId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
