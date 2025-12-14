package com.example.bds.modules.iam.dto.Permission;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePermissionDTO {
    @NotNull(message = "Permission ID is required")
    private Long id;

    @Size(max = 100, message = "Permission name must be less than 100 characters")
    private String name;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @Size(max = 50, message = "Module must be less than 50 characters")
    private String module;

    private Boolean isActive;
}

