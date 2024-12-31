package io.codeidea.apitemplate.admin.domain;

import static org.assertj.core.api.Assertions.*;

import io.codeidea.apitemplate.mock.TestTimeHolder;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class AdminTest {

    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void AdminSignUp_으로_생성할_수_있다() {
        // given
        AdminSignUp adminSignUp = new AdminSignUp("foo", "bar", "baz", AdminRole.NORMAL);

        LocalDateTime createdAt = LocalDateTime.now();

        // when
        Admin admin =
                Admin.signUp(adminSignUp, new TestTimeHolder(createdAt).getTime(), passwordEncoder);

        // then
        assertThat(admin.getId()).isNull();
        assertThat(admin.getName()).isEqualTo("foo");
        assertThat(admin.getLoginId()).isEqualTo("bar");
        assertThat(passwordEncoder.matches("baz", admin.getPassword())).isTrue();
        assertThat(admin.getRole()).isEqualTo(AdminRole.NORMAL);
        assertThat(admin.getCreatedAt()).isEqualTo(createdAt);
        assertThat(admin.getUpdatedAt()).isEqualTo(createdAt);
        assertThat(admin.getLastLoginAt()).isNull();
    }

    @Test
    void AdminUpdate로_수정할_수_있다() {
        // given
        LocalDateTime createdAt = LocalDateTime.now();
        Admin admin =
                Admin.create(1L, "foo", "bar", "baz", AdminRole.NORMAL, createdAt, createdAt, null);
        LocalDateTime updatedAt = LocalDateTime.now();

        // when
        Admin updatedAdmin =
                admin.update(
                        new AdminUpdate("new_foo", "new_baz", AdminRole.SUPER),
                        updatedAt,
                        passwordEncoder);

        // then
        assertThat(updatedAdmin.getId()).isEqualTo(1L);
        assertThat(updatedAdmin.getName()).isEqualTo("new_foo");
        assertThat(updatedAdmin.getLoginId()).isEqualTo("bar");
        assertThat(passwordEncoder.matches("new_baz", admin.getPassword())).isTrue();
        assertThat(updatedAdmin.getRole()).isEqualTo(AdminRole.SUPER);
        assertThat(updatedAdmin.getCreatedAt()).isEqualTo(createdAt);
        assertThat(updatedAdmin.getUpdatedAt()).isEqualTo(updatedAt);
        assertThat(updatedAdmin.getLastLoginAt()).isNull();
    }
}
