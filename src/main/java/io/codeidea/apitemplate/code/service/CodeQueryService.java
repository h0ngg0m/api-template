package io.codeidea.apitemplate.code.service;

import io.codeidea.apitemplate.code.service.response.CodeResponse;
import io.codeidea.apitemplate.common.request.PaginationRequest;
import org.springframework.data.domain.Page;

public interface CodeQueryService {

    Page<CodeResponse> findByPagination(PaginationRequest paginationRequest);
}
