package io.codeidea.apitemplate.admin.service;

import io.codeidea.apitemplate.admin.domain.Admin;
import io.codeidea.apitemplate.admin.domain.AdminSignIn;
import io.codeidea.apitemplate.admin.domain.AdminSignUp;
import io.codeidea.apitemplate.admin.domain.AdminUpdate;
import io.codeidea.apitemplate.admin.service.port.AdminRepository;
import io.codeidea.apitemplate.admin.service.response.AdminResponse;
import io.codeidea.apitemplate.common.exception.custom.CustomException;
import io.codeidea.apitemplate.common.exception.custom.UnauthorizedException;
import io.codeidea.apitemplate.common.infrastructure.TimeHolder;
import io.codeidea.apitemplate.common.infrastructure.jwt.Jwt;
import io.codeidea.apitemplate.common.infrastructure.jwt.JwtProvider;
import io.codeidea.apitemplate.common.response.ApiResponseCode;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCommandServiceImpl implements AdminCommandService {

    private final AdminRepository adminRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final TimeHolder timeHolder;

    @Override
    public Jwt signIn(AdminSignIn adminSignIn) {
        Admin admin =
                adminRepository
                        .findByLoginId(adminSignIn.loginId())
                        .orElseThrow(() -> new UnauthorizedException(ApiResponseCode.UNAUTHORIZED));

        if (admin.signIn(adminSignIn.password(), timeHolder.getTime(), passwordEncoder)) {
            adminRepository.save(admin);
            return new Jwt(jwtProvider.generateAccessToken(generateClaims(admin)));
        }

        throw new UnauthorizedException(ApiResponseCode.UNAUTHORIZED);
    }

    @Override
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
    public void update(Long id, AdminUpdate adminUpdate) {
        Admin admin =
                adminRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new CustomException(
                                                ApiResponseCode.ADMIN_NOT_FOUND.customMessage(
                                                        "The admin cannot be found. id: " + id)));
        adminRepository.save(admin.update(adminUpdate, timeHolder.getTime(), passwordEncoder));
    }

    @Override
    public void delete(Long id) {
        adminRepository.deleteById(id);
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
