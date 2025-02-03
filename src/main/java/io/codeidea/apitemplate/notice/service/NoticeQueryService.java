package io.codeidea.apitemplate.notice.service;

import io.codeidea.apitemplate.common.request.PaginationRequest;
import io.codeidea.apitemplate.notice.service.response.NoticeResponse;
import org.springframework.data.domain.Page;

public interface NoticeQueryService {

    Page<NoticeResponse> findByPagination(PaginationRequest paginationRequest);

    NoticeResponse findById(Long id);
}
