package io.codeidea.apitemplate.admin.service;

import io.codeidea.apitemplate.admin.domain.Admin;
import io.codeidea.apitemplate.admin.domain.AdminSignIn;
import io.codeidea.apitemplate.admin.domain.AdminSignUp;
import io.codeidea.apitemplate.admin.domain.AdminUpdate;
import io.codeidea.apitemplate.admin.service.port.AdminRepository;
import io.codeidea.apitemplate.admin.service.response.AdminResponse;
import io.codeidea.apitemplate.common.exception.custom.CustomException;
import io.codeidea.apitemplate.common.exception.custom.UnauthorizedException;
import io.codeidea.apitemplate.common.infrastructure.jwt.Jwt;
import io.codeidea.apitemplate.common.infrastructure.jwt.JwtProvider;
import io.codeidea.apitemplate.common.request.PaginationRequest;
import io.codeidea.apitemplate.common.response.ApiResponseCode;
import io.codeidea.apitemplate.common.infrastructure.TimeHolder;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final TimeHolder timeHolder;

    @Override
    @Transactional(readOnly = true)
    public Page<AdminResponse> findByPagination(PaginationRequest paginationRequest) {
        return adminRepository
                .findByPagination(paginationRequest.getPageRequest())
                .map(AdminResponse::new);
    }

    @Override
    @Transactional
    public Jwt signIn(AdminSignIn adminSignIn) {
        Admin admin =
                adminRepository
                        .findByLoginId(adminSignIn.loginId())
                        .orElseThrow(() -> new UnauthorizedException(ApiResponseCode.UNAUTHORIZED));

        if (!passwordEncoder.matches(adminSignIn.password(), admin.getPassword())) {
            throw new UnauthorizedException(ApiResponseCode.UNAUTHORIZED);
        }

        admin.setLastLoginAt(timeHolder.getTime());
        adminRepository.save(admin);
        return new Jwt(jwtProvider.generateAccessToken(generateClaims(admin)));
    }

    @Override
    @Transactional
    public AdminResponse signUp(AdminSignUp adminSignUp) {
        if (adminRepository.existsByLoginId(adminSignUp.getLoginId())) {
            throw new CustomException(
                    ApiResponseCode.ADMIN_ALREADY_EXISTS.customMessage(
                            "The admin already exists. loginId: " + adminSignUp.getLoginId()));
        }
        return new AdminResponse(
                adminRepository.save(
                        Admin.signUp(adminSignUp, timeHolder.getTime(), passwordEncoder)));
    }

    @Override
    @Transactional
    public AdminResponse update(Long id, AdminUpdate adminUpdate) {
        Admin admin =
                adminRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new CustomException(
                                                ApiResponseCode.ADMIN_NOT_FOUND.customMessage(
                                                        "The admin cannot be found. id: " + id)));

        return new AdminResponse(
                adminRepository.save(
                        admin.update(adminUpdate, timeHolder.getTime(), passwordEncoder)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public AdminResponse findById(Long id) {
        return new AdminResponse(
                adminRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new CustomException(
                                                ApiResponseCode.ADMIN_NOT_FOUND.customMessage(
                                                        "The admin cannot be found. id: " + id))));
    }

    @Override
    public AdminResponse findByLoginId(String loginId) {
        return new AdminResponse(
                adminRepository
                        .findByLoginId(loginId)
                        .orElseThrow(
                                () ->
                                        new CustomException(
                                                ApiResponseCode.ADMIN_NOT_FOUND.customMessage(
                                                        "The admin cannot be found. loginId: "
                                                                + loginId))));
    }

    private Map<String, Object> generateClaims(Admin admin) {
        return Map.of(
                "id",
                admin.getId(),
                "name",
                admin.getName(),
                "loginId",
                admin.getLoginId(),
                "role",
                admin.getRole());
    }
}
