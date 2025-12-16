package com.example.bds.modules.iam.controller;

import com.example.bds.modules.iam.dto.UserRole.UserRoleDTO;
import com.example.bds.modules.iam.dto.UserRole.CreateUserRoleDTO;
import com.example.bds.modules.iam.dto.UserRole.UpdateUserRoleDTO;
import com.example.bds.modules.iam.service.UserRoleService;
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
@RequestMapping("/api/user-roles")
@Tag(name = "User Role Management", description = "APIs quản lý phân quyền user-role trong hệ thống IAM")
public class UserRoleController {
    private UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }
    // Các endpoint để quản lý UserRole

    // Lấy tất cả UserRoleDTO theo userId
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<UserRoleDTO>> getUserRolesByUserId(@PathVariable Long userId) {
        List<UserRoleDTO> userRoles = userRoleService.getUserRolesByUserId(userId);
        return ResponseEntity.ok(userRoles);
    }

    // Thêm UserRole từ CreateUserRoleDTO
    @PostMapping
    public ResponseEntity<UserRoleDTO> addUserRole(@RequestBody CreateUserRoleDTO createDTO) {
        try {
            UserRoleDTO userRoleDTO = userRoleService.addUserRole(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(userRoleDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Cập nhật UserRole từ UpdateUserRoleDTO
    @PutMapping("/{id}")
    public ResponseEntity<UserRoleDTO> updateUserRole(@PathVariable Long id, @RequestBody UpdateUserRoleDTO updateDTO) {
        try {
            UserRoleDTO userRoleDTO = userRoleService.updateUserRole(id, updateDTO);
            return ResponseEntity.ok(userRoleDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Xóa UserRole theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable Long id) {
        try {
            userRoleService.deleteUserRole(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
