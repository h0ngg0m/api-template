package io.codeidea.apitemplate.admin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.codeidea.apitemplate.admin.domain.*;
import io.codeidea.apitemplate.admin.service.port.AdminRepository;
import io.codeidea.apitemplate.admin.service.response.AdminResponse;
import io.codeidea.apitemplate.common.exception.custom.CustomException;
import io.codeidea.apitemplate.common.exception.custom.UnauthorizedException;
import io.codeidea.apitemplate.common.infrastructure.jwt.Jwt;
import io.codeidea.apitemplate.common.infrastructure.jwt.SystemJwtProvider;
import io.codeidea.apitemplate.mock.FakeAdminRepository;
import io.codeidea.apitemplate.mock.TestJwtPropertiesHolder;
import io.codeidea.apitemplate.mock.TestTimeHolder;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class AdminCommandServiceImplTest {

    private AdminCommandService adminCommandService;
    private Admin sampleAdmin;
    private AdminRepository adminRepository;

    @BeforeEach
    void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        adminRepository = new FakeAdminRepository();

        sampleAdmin =
                adminRepository.save(
                        Admin.of(
                                null,
                                "admin",
                                "admin",
                                passwordEncoder.encode("1234"),
                                AdminRole.SUPER,
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                null));

        adminRepository.save(
                Admin.of(
                        null,
                        "admin2",
                        "admin2",
                        passwordEncoder.encode("1234"),
                        AdminRole.SUPER,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        null));

        adminRepository.save(
                Admin.of(
                        null,
                        "admin3",
                        "admin3",
                        passwordEncoder.encode("1234"),
                        AdminRole.SUPER,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        null));

        adminRepository.save(
                Admin.of(
                        null,
                        "admin4",
                        "admin4",
                        passwordEncoder.encode("1234"),
                        AdminRole.SUPER,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        null));

        adminCommandService =
                new AdminCommandServiceImpl(
                        adminRepository,
                        new SystemJwtProvider(new TestJwtPropertiesHolder()),
                        passwordEncoder,
                        new TestTimeHolder(LocalDateTime.of(2021, 1, 1, 0, 0)));
    }

    @Test
    void 관리자가_로그인에_성공하면_JWT를_반환받는다() {
        // given
        AdminSignIn adminSignIn = new AdminSignIn("admin", "1234");

        // when
        Jwt jwt = adminCommandService.signIn(adminSignIn);

        // then
        assertThat(jwt).isNotNull();
    }

    @Test
    void 관리자가_로그인에_성공하면_최종_로그인_일시가_갱신된다() {
        // given
        AdminSignIn adminSignIn = new AdminSignIn("admin", "1234");

        // when
        adminCommandService.signIn(adminSignIn);
        Admin admin = adminRepository.findByLoginId("admin").get();

        // then
        assertThat(admin.getLastLoginAt()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0));
    }

    @Test
    void 관리자가_존재하지_않는_아이디로_로그인을_시도하면_예외가_발생한다() {
        // given
        AdminSignIn adminSignIn = new AdminSignIn("not_exists", "1234");

        // when & then
        assertThatThrownBy(() -> adminCommandService.signIn(adminSignIn))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("Unauthorized");
    }

    @Test
    void 관리자가_틀린_비밀번호로_로그인을_시도하면_예외가_발생한다() {
        // given
        AdminSignIn adminSignIn = new AdminSignIn("admin", "wrong_password");

        // when & then
        assertThatThrownBy(() -> adminCommandService.signIn(adminSignIn))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("Unauthorized");
    }

    @Test
    void 관리자는_회원가입을_할_수_있다() {
        // given
        AdminSignUp adminSignUp =
                new AdminSignUp("new_admin", "new_admin", "1234", AdminRole.SUPER);

        // when
        AdminResponse admin = adminCommandService.signUp(adminSignUp);

        // then
        assertThat(admin.id()).isNotNull();
        assertThat(admin.name()).isEqualTo("new_admin");
        assertThat(admin.loginId()).isEqualTo("new_admin");
        assertThat(admin.role()).isEqualTo(AdminRole.SUPER);
        assertThat(admin.createdAt()).isNotNull();
        assertThat(admin.updatedAt()).isNotNull();
        assertThat(admin.lastLoginAt()).isNull();
    }

    @Test
    void 관리자가_이미_등록된_아이디로_회원가입을_시도히면_예외가_발생한다() {
        // given
        AdminSignUp adminSignUp = new AdminSignUp("new_admin", "admin", "1234", AdminRole.SUPER);

        // when & then
        assertThatThrownBy(() -> adminCommandService.signUp(adminSignUp))
                .isInstanceOf(CustomException.class)
                .hasMessage("The admin already exists. loginId: admin");
    }

    @Test
    void 관리자가_정보를_수정할_때_새로운_비밀번호를_입력하면_비밀번호를_포함하여_정보가_수정된다() {
        // given
        String oldPassword = sampleAdmin.getPassword();
        AdminUpdate adminUpdate =
                new AdminUpdate("update_name", "update_password", AdminRole.NORMAL);

        // when
        adminCommandService.update(sampleAdmin.getId(), adminUpdate);

        // then
        Admin updatedAdmin = adminRepository.findById(sampleAdmin.getId()).get();

        assertThat(updatedAdmin.getId()).isEqualTo(sampleAdmin.getId());
        assertThat(updatedAdmin.getName()).isEqualTo("update_name");
        assertThat(updatedAdmin.getPassword()).isNotNull();
        assertThat(updatedAdmin.getPassword()).isNotEqualTo(oldPassword);
        assertThat(updatedAdmin.getRole()).isEqualTo(AdminRole.NORMAL);
        assertThat(updatedAdmin.getUpdatedAt()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0));
        assertThat(updatedAdmin.getCreatedAt()).isNotNull();
        assertThat(updatedAdmin.getLastLoginAt()).isNull();
    }

    @Test
    void 관리자가_정보를_수정할_때_새로운_비밀번호를_입력하지_않으면_비밀번호를_제외하고_정보가_수정된다() {
        // given
        String oldPassword = sampleAdmin.getPassword();
        AdminUpdate adminUpdate = new AdminUpdate("update_name", null, AdminRole.NORMAL);

        // when
        adminCommandService.update(sampleAdmin.getId(), adminUpdate);

        // then
        Admin updatedAdmin = adminRepository.findById(sampleAdmin.getId()).get();

        assertThat(updatedAdmin.getId()).isEqualTo(sampleAdmin.getId());
        assertThat(updatedAdmin.getName()).isEqualTo("update_name");
        assertThat(updatedAdmin.getPassword()).isNotNull();
        assertThat(updatedAdmin.getPassword()).isEqualTo(oldPassword);
        assertThat(updatedAdmin.getRole()).isEqualTo(AdminRole.NORMAL);
        assertThat(updatedAdmin.getUpdatedAt()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0));
        assertThat(updatedAdmin.getCreatedAt()).isNotNull();
        assertThat(updatedAdmin.getLastLoginAt()).isNull();
    }

    @Test
    void 관리자의_정보를_수정할_때_존재하지_않는_PK로_조회하면_예외가_발생한다() {
        // given
        AdminUpdate adminUpdate =
                new AdminUpdate("update_name", "update_password", AdminRole.NORMAL);

        // when & then
        assertThatThrownBy(() -> adminCommandService.update(999L, adminUpdate))
                .isInstanceOf(CustomException.class)
                .hasMessage("The admin cannot be found. id: 999");
    }

    @Test
    void 관리자를_PK로_삭제할_수_있다() {
        // given
        Long id = sampleAdmin.getId();

        // when
        adminCommandService.delete(id);

        // then
        assertThat(adminRepository.findById(id)).isEmpty();
    }
}
