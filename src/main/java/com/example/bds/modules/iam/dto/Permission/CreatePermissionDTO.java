package com.example.bds.modules.iam.dto.Permission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePermissionDTO {
    @NotBlank(message = "Permission name is required")
    @Size(max = 100, message = "Permission name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @NotBlank(message = "Module is required")
    @Size(max = 50, message = "Module must be less than 50 characters")
    private String module; // VD: "USER", "PRODUCT", "ORDER"

    private Boolean isActive = true;
}

