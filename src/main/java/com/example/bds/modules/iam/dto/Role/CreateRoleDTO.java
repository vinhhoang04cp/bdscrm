package com.example.bds.modules.iam.dto.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoleDTO {
    @NotBlank(message = "Role name is required")
    @Size(max = 100, message = "Role name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    private Boolean isAdminRole = false;

    private Boolean isActive = true;
}
