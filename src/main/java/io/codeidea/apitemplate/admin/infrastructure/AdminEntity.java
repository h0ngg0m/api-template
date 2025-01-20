package io.codeidea.apitemplate.admin.infrastructure;

import io.codeidea.apitemplate.admin.domain.Admin;
import io.codeidea.apitemplate.admin.domain.AdminRole;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "admin")
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String loginId;
    private String password;

    @Enumerated(EnumType.STRING)
    private AdminRole role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Setter private LocalDateTime lastLoginAt;

    public Admin toDomain() {
        return Admin.of(id, name, loginId, password, role, createdAt, updatedAt, lastLoginAt);
    }

    public static AdminEntity from(Admin admin) {
        return new AdminEntity(
                admin.getId(),
                admin.getName(),
                admin.getLoginId(),
                admin.getPassword(),
                admin.getRole(),
                admin.getCreatedAt(),
                admin.getUpdatedAt(),
                admin.getLastLoginAt());
    }
}
