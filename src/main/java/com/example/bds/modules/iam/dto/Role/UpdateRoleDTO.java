package com.example.bds.modules.iam.dto.Role;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleDTO {
    @NotNull(message = "Role ID is required")
    private Long id;

    @Size(max = 100, message = "Role name must be less than 100 characters")
    private String name;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    private Boolean isAdminRole;

    private Boolean isActive;
}
