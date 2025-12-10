package com.example.bds.modules.iam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "role_permissions",
    uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "permission_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"role", "permission"})
@EqualsAndHashCode(of = {"id"})
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonIgnoreProperties({"users", "permissions"})
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY) //
    @JoinColumn(name = "permission_id", nullable = false)
    @JsonIgnoreProperties({"roles"})
    private Permission permission;

    @Column(name = "granted_at", nullable = false, updatable = false)
    private LocalDateTime grantedAt;

    @Column(name = "granted_by")
    private Long grantedBy;

    @PrePersist
    protected void onCreate() {
        this.grantedAt = LocalDateTime.now();
    }
}

