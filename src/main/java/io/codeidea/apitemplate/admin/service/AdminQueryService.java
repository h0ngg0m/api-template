package io.codeidea.apitemplate.admin.service;

import io.codeidea.apitemplate.admin.service.response.AdminResponse;
import io.codeidea.apitemplate.common.request.PaginationRequest;
import org.springframework.data.domain.Page;

public interface AdminQueryService {

    AdminResponse findById(Long id);

    AdminResponse findByLoginId(String loginId);

    Page<AdminResponse> findByPagination(PaginationRequest paginationRequest);
}
