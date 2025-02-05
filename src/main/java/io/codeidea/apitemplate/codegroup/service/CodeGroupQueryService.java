package io.codeidea.apitemplate.codegroup.service;

import io.codeidea.apitemplate.codegroup.service.response.CodeGroupResponse;
import io.codeidea.apitemplate.common.request.PaginationRequest;
import org.springframework.data.domain.Page;

public interface CodeGroupQueryService {

    Page<CodeGroupResponse> findByPagination(PaginationRequest paginationRequest);
}
