package io.codeidea.apitemplate.admin.service;

import static org.assertj.core.api.Assertions.*;

import io.codeidea.apitemplate.admin.domain.*;
import io.codeidea.apitemplate.admin.service.port.AdminRepository;
import io.codeidea.apitemplate.admin.service.response.AdminResponse;
import io.codeidea.apitemplate.common.request.PaginationRequest;
import io.codeidea.apitemplate.mock.FakeAdminRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class AdminQueryServiceImplTest {

    private AdminQueryService adminQueryService;
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

        adminQueryService = new AdminQueryServiceImpl(adminRepository);
    }

    @Test
    void 관리자들을_페이징으로_조회할_수_있다() {
        // given
        PaginationRequest paginationRequest = new PaginationRequest(0, 2, "id", false);

        // when
        Page<AdminResponse> admins = adminQueryService.findByPagination(paginationRequest);

        // then
        assertThat(admins.getTotalElements()).isEqualTo(4);
        assertThat(admins.getTotalPages()).isEqualTo(2);
        assertThat(admins.getContent().size()).isEqualTo(2);
        assertThat(admins.hasNext()).isTrue();
    }

    @Test
    void 관리자를_PK로_조회할_수_있다() {
        // when
        AdminResponse admin = adminQueryService.findById(sampleAdmin.getId());

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
    void 관리자를_아이디로_조회할_수_있다() {
        // when
        AdminResponse admin = adminQueryService.findByLoginId(sampleAdmin.getLoginId());

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
