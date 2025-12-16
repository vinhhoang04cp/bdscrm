package com.example.bds.modules.iam.controller;

import com.example.bds.modules.iam.dto.Permission.UpdatePermissionDTO;
import com.example.bds.modules.iam.dto.Permission.PermissionDTO;
import com.example.bds.modules.iam.dto.Permission.CreatePermissionDTO;
import com.example.bds.modules.iam.service.PermissionService;
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
@RequestMapping("/api/permissions")
@Tag(name = "Permission Management", description = "APIs quản lý quyền/permission trong hệ thống IAM")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public ResponseEntity<List<PermissionDTO>> getAllPermissions() {
        List<PermissionDTO> permissions = permissionService.findAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionDTO> getPermissionById(@PathVariable Long id) {
        try {
            PermissionDTO permissionDTO = permissionService.findPermissionById(id);
            return ResponseEntity.ok(permissionDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<PermissionDTO> getPermissionByName(@PathVariable String name) {
        try {
            PermissionDTO permissionDTO = permissionService.findPermissionByName(name);
            return ResponseEntity.ok(permissionDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/by-module/{module}")
    public ResponseEntity<PermissionDTO> getPermissionByModule(@PathVariable String module) {
        try {
            PermissionDTO permissionDTO = permissionService.findPermissionByModule(module);
            return ResponseEntity.ok(permissionDTO); }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/by-active/{isActive}")
    public ResponseEntity<List<PermissionDTO>> getPermissionsByIsActive(@PathVariable Boolean isActive) {
        List<PermissionDTO> permissions = permissionService.findPermissionsByIsActive(isActive);
        return ResponseEntity.ok(permissions);
    }

    // Tạo mới Permission
    @PostMapping
    public ResponseEntity<PermissionDTO> createPermission(@RequestBody CreatePermissionDTO createPermissionDTO) {
        try {
            PermissionDTO permissionDTO = permissionService.createPermission(createPermissionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(permissionDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Update Permission
    @PutMapping("/{id}")
    public ResponseEntity<PermissionDTO> updatePermission(@PathVariable Long id, @RequestBody UpdatePermissionDTO updatePermissionDTO) {
        try {
            updatePermissionDTO.setId(id); // Set id vào DTO
            PermissionDTO permissionDTO = permissionService.updatePermission(updatePermissionDTO);
            return ResponseEntity.ok(permissionDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Xóa Permission
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        try {
            permissionService.deletePermission(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
