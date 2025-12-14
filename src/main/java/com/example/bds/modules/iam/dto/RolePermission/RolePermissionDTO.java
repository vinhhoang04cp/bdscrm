package com.example.bds.modules.iam.dto.RolePermission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionDTO {
    private Long id;
    private Long roleId;
    private String roleName;
    private Long permissionId;
    private String permissionName;
    private String permissionModule;
    private LocalDateTime grantedAt;
    private Long grantedBy;
}

