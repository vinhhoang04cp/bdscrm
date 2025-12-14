package com.example.bds.modules.iam.dto.RolePermission;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignPermissionsToRoleDTO {
    @NotNull(message = "Role ID is required")
    private Long roleId;

    @NotNull(message = "Permission IDs are required")
    private List<Long> permissionIds;

    private Long grantedBy;
}

