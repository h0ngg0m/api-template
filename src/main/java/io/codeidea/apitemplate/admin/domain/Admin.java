package io.codeidea.apitemplate.admin.domain;

import java.time.LocalDateTime;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Admin {

    private Long id;
    private String name;
    private String loginId;
    private String password;
    private AdminRole role;
    private LocalDateTime createdAt;
    @Setter private LocalDateTime updatedAt;
    @Setter private LocalDateTime lastLoginAt;

    public static Admin signUp(
            AdminSignUp adminSignUp, LocalDateTime createdAt, PasswordEncoder passwordEncoder) {
        adminSignUp.setPassword(passwordEncoder.encode(adminSignUp.getPassword()));
        return new Admin(
                null,
                adminSignUp.getName(),
                adminSignUp.getLoginId(),
                adminSignUp.getPassword(),
                adminSignUp.getRole(),
                createdAt,
                createdAt,
                null);
    }

    public Admin update(
            AdminUpdate adminUpdate, LocalDateTime updatedAt, PasswordEncoder passwordEncoder) {
        if (StringUtils.hasText(adminUpdate.password())) {
            this.password = passwordEncoder.encode(adminUpdate.password());
        }
        this.name = adminUpdate.name();
        this.role = adminUpdate.role();
        setUpdatedAt(updatedAt);
        return this;
    }

    public static Admin of(
            final Long id,
            final String name,
            final String loginId,
            final String password,
            final AdminRole role,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final LocalDateTime lastLoginAt) {
        return new Admin(id, name, loginId, password, role, createdAt, updatedAt, lastLoginAt);
    }

    public boolean signIn(
            String inputPassword, LocalDateTime lastLoginAt, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(inputPassword, this.getPassword())) {
            return false;
        }
        this.setLastLoginAt(lastLoginAt);
        return true;
    }
}
