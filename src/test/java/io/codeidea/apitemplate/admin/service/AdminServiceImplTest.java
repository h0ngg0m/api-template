package io.codeidea.apitemplate.admin.service;

import static org.assertj.core.api.Assertions.*;

import io.codeidea.apitemplate.admin.domain.*;
import io.codeidea.apitemplate.admin.service.port.AdminRepository;
import io.codeidea.apitemplate.admin.service.response.AdminResponse;
import io.codeidea.apitemplate.common.exception.custom.CustomException;
import io.codeidea.apitemplate.common.exception.custom.UnauthorizedException;
import io.codeidea.apitemplate.common.infrastructure.jwt.Jwt;
import io.codeidea.apitemplate.common.infrastructure.jwt.SystemJwtProvider;
import io.codeidea.apitemplate.common.request.PaginationRequest;
import io.codeidea.apitemplate.mock.FakeAdminRepository;
import io.codeidea.apitemplate.mock.TestJwtPropertiesHolder;
import io.codeidea.apitemplate.mock.TestTimeHolder;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class AdminServiceImplTest {

    private AdminService adminService;
    private Admin sampleAdmin;
    private AdminRepository adminRepository;

    @BeforeEach
    void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        adminRepository = new FakeAdminRepository();

        sampleAdmin =
                adminRepository.save(
                        Admin.create(
                                null,
                                "admin",
                                "admin",
                                passwordEncoder.encode("1234"),
                                AdminRole.SUPER,
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                null));

        adminRepository.save(
                Admin.create(
                        null,
                        "admin2",
                        "admin2",
                        passwordEncoder.encode("1234"),
                        AdminRole.SUPER,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        null));

        adminRepository.save(
                Admin.create(
                        null,
                        "admin3",
                        "admin3",
                        passwordEncoder.encode("1234"),
                        AdminRole.SUPER,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        null));

        adminRepository.save(
                Admin.create(
                        null,
                        "admin4",
                        "admin4",
                        passwordEncoder.encode("1234"),
                        AdminRole.SUPER,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        null));

        adminService =
                new AdminServiceImpl(
                        adminRepository,
                        new SystemJwtProvider(new TestJwtPropertiesHolder()),
                        passwordEncoder,
                        new TestTimeHolder(LocalDateTime.of(2021, 1, 1, 0, 0)));
    }

    @Test
    void Admin들을_페이징하여_조회할_수_있다() {
        // given
        PaginationRequest paginationRequest = new PaginationRequest(1, 2, "id", false);

        // when
        Page<AdminResponse> admins = adminService.findByPagination(paginationRequest);

        // then
        assertThat(admins.getTotalElements()).isEqualTo(4);
        assertThat(admins.getTotalPages()).isEqualTo(2);
        assertThat(admins.getContent().size()).isEqualTo(2);
        assertThat(admins.hasNext()).isTrue();
    }

    @Test
    void 로그인을_성공하면_jwt를_반환_받을_수_있다() {
        // given
        AdminSignIn adminSignIn = new AdminSignIn("admin", "1234");

        // when
        Jwt jwt = adminService.signIn(adminSignIn);

        // then
        assertThat(jwt).isNotNull();
    }

    @Test
    void 로그인_성공_시_lastLoginAt이_갱신된다() {
        // given
        AdminSignIn adminSignIn = new AdminSignIn("admin", "1234");

        // when
        adminService.signIn(adminSignIn);
        Admin admin = adminRepository.findByLoginId("admin").get();

        // then
        assertThat(admin.getLastLoginAt()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0));
    }

    @Test
    void 존재하지_않는_loginId로_로그인을_시도하면_예외가_발생한다() {
        // given
        AdminSignIn adminSignIn = new AdminSignIn("not_exists", "1234");

        // when & then
        assertThatThrownBy(() -> adminService.signIn(adminSignIn))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("Unauthorized");
    }

    @Test
    void 틀린_비밀번호로_로그인을_시도하면_예외가_발생한다() {
        // given
        AdminSignIn adminSignIn = new AdminSignIn("admin", "wrong_password");

        // when & then
        assertThatThrownBy(() -> adminService.signIn(adminSignIn))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("Unauthorized");
    }

    @Test
    void 가입을_성공한다() {
        // given
        AdminSignUp adminSignUp =
                new AdminSignUp("new_admin", "new_admin", "1234", AdminRole.SUPER);

        // when
        AdminResponse admin = adminService.signUp(adminSignUp);

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
    void 이미_존재하는_loginId로_회원가입을_시도하면_예외가_발생한다() {
        // given
        AdminSignUp adminSignUp = new AdminSignUp("new_admin", "admin", "1234", AdminRole.SUPER);

        // when & then
        assertThatThrownBy(() -> adminService.signUp(adminSignUp))
                .isInstanceOf(CustomException.class)
                .hasMessage("The admin already exists. loginId: admin");
    }

    @Test
    void 정보를_수정한다__새로운_비밀번호를_포함하면_비밀번호도_변경된다() {
        // given
        String oldPassword = sampleAdmin.getPassword();
        AdminUpdate adminUpdate =
                new AdminUpdate("update_name", "update_password", AdminRole.NORMAL);

        // when
        adminService.update(sampleAdmin.getId(), adminUpdate);

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
    void 정보를_수정한다__비밀번호를_포함하지_않으면_기존_비밀번호로_유지된다() {
        // given
        String oldPassword = sampleAdmin.getPassword();
        AdminUpdate adminUpdate = new AdminUpdate("update_name", null, AdminRole.NORMAL);

        // when
        adminService.update(sampleAdmin.getId(), adminUpdate);

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
    void 존재하지_않는_id로_정보_수정을_시도하면_예외가_발생한다() {
        // given
        AdminUpdate adminUpdate =
                new AdminUpdate("update_name", "update_password", AdminRole.NORMAL);

        // when & then
        assertThatThrownBy(() -> adminService.update(999L, adminUpdate))
                .isInstanceOf(CustomException.class)
                .hasMessage("The admin cannot be found. id: 999");
    }

    @Test
    void Admin을_삭제할_수_있다() {
        // when
        adminService.delete(sampleAdmin.getId());

        // then
        assertThatThrownBy(() -> adminService.findById(sampleAdmin.getId()))
                .isInstanceOf(CustomException.class);
    }

    @Test
    void id로_조회할_수_있다() {
        // when
        AdminResponse admin = adminService.findById(sampleAdmin.getId());

        // then
        assertThat(admin.id()).isEqualTo(sampleAdmin.getId());
        assertThat(admin.name()).isEqualTo(sampleAdmin.getName());
        assertThat(admin.loginId()).isEqualTo(sampleAdmin.getLoginId());
        assertThat(admin.role()).isEqualTo(sampleAdmin.getRole());
        assertThat(admin.createdAt()).isEqualTo(sampleAdmin.getCreatedAt());
        assertThat(admin.updatedAt()).isEqualTo(sampleAdmin.getUpdatedAt());
        assertThat(admin.lastLoginAt()).isEqualTo(sampleAdmin.getLastLoginAt());
    }

    @Test
    void loginId로_조회할_수_있다() {
        // when
        AdminResponse admin = adminService.findByLoginId(sampleAdmin.getLoginId());

        // then
        assertThat(admin.id()).isEqualTo(sampleAdmin.getId());
        assertThat(admin.name()).isEqualTo(sampleAdmin.getName());
        assertThat(admin.loginId()).isEqualTo(sampleAdmin.getLoginId());
        assertThat(admin.role()).isEqualTo(sampleAdmin.getRole());
        assertThat(admin.createdAt()).isEqualTo(sampleAdmin.getCreatedAt());
        assertThat(admin.updatedAt()).isEqualTo(sampleAdmin.getUpdatedAt());
        assertThat(admin.lastLoginAt()).isEqualTo(sampleAdmin.getLastLoginAt());
    }
}
