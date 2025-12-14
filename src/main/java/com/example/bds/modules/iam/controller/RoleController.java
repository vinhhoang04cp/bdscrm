package com.example.bds.modules.iam.controller;

import com.example.bds.modules.iam.dto.Role.CreateRoleDTO;
import com.example.bds.modules.iam.dto.Role.UpdateRoleDTO;
import com.example.bds.modules.iam.dto.Role.RoleDTO;
import com.example.bds.modules.iam.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Đánh dấu lớp này là một REST controller
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        try {
            RoleDTO roleDTO = roleService.getRoleById(id);
            return ResponseEntity.ok(roleDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody CreateRoleDTO createRoleDTO) {
        try {
            RoleDTO roleDTO = roleService.createRole(createRoleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(roleDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping
    public ResponseEntity<RoleDTO> updateRole(@RequestBody UpdateRoleDTO updateRoleDTO) {
        try {
            RoleDTO roleDTO = roleService.updateRole(updateRoleDTO);
            return ResponseEntity.ok(roleDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
