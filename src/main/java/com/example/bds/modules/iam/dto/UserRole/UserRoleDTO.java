package com.example.bds.modules.iam.dto.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDTO {
    private Long id;
    private Long userId;
    private String userName;
    private String roleName;
    private Long roleId;
    private LocalDateTime assignedAt;
    private LocalDateTime revokedAt;
    private Long assignedBy;
}
