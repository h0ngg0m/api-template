package io.codeidea.apitemplate.admin.service.response;

import io.codeidea.apitemplate.admin.domain.Admin;
import io.codeidea.apitemplate.admin.domain.AdminRole;
import java.time.LocalDateTime;

public record AdminResponse(
        Long id,
        String name,
        String loginId,
        AdminRole role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime lastLoginAt) {
    public AdminResponse(Admin admin) {
        this(
                admin.getId(),
                admin.getName(),
                admin.getLoginId(),
                admin.getRole(),
                admin.getCreatedAt(),
                admin.getUpdatedAt(),
                admin.getLastLoginAt());
    }
}
