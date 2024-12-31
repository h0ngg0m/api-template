package io.codeidea.apitemplate.admin.service;

import io.codeidea.apitemplate.admin.domain.AdminSignIn;
import io.codeidea.apitemplate.admin.domain.AdminSignUp;
import io.codeidea.apitemplate.admin.domain.AdminUpdate;
import io.codeidea.apitemplate.admin.service.response.AdminResponse;
import io.codeidea.apitemplate.common.infrastructure.jwt.Jwt;
import io.codeidea.apitemplate.common.request.PaginationRequest;
import org.springframework.data.domain.Page;

public interface AdminService {

    Jwt signIn(AdminSignIn adminSignIn);

    AdminResponse signUp(AdminSignUp adminSignUp);

    AdminResponse update(Long id, AdminUpdate adminUpdate);

    void delete(Long id);

    AdminResponse findById(Long id);

    AdminResponse findByLoginId(String loginId);

    Page<AdminResponse> findByPagination(PaginationRequest paginationRequest);
}
