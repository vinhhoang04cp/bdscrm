package com.example.bds.modules.iam.dto.Permission;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionDTO {
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private Boolean isActive;
    private String module;
    private String description;
    private String name;
    private Long id;
}




