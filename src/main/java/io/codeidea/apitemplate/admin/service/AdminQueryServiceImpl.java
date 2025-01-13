package io.codeidea.apitemplate.admin.service;

import io.codeidea.apitemplate.admin.service.port.AdminRepository;
import io.codeidea.apitemplate.admin.service.response.AdminResponse;
import io.codeidea.apitemplate.common.exception.custom.CustomException;
import io.codeidea.apitemplate.common.request.PaginationRequest;
import io.codeidea.apitemplate.common.response.ApiResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminQueryServiceImpl implements AdminQueryService {

    private final AdminRepository adminRepository;

    @Override
    public Page<AdminResponse> findByPagination(PaginationRequest paginationRequest) {
        return adminRepository
                .findByPagination(paginationRequest.getPageRequest())
                .map(AdminResponse::new);
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
}
