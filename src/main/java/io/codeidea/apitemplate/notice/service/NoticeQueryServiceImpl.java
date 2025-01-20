package io.codeidea.apitemplate.notice.service;

import io.codeidea.apitemplate.common.request.PaginationRequest;
import io.codeidea.apitemplate.notice.service.port.NoticeRepository;
import io.codeidea.apitemplate.notice.service.response.NoticeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeQueryServiceImpl implements NoticeQueryService {

    private final NoticeRepository noticeRepository;

    @Override
    public Page<NoticeResponse> findByPagination(PaginationRequest paginationRequest) {
        return noticeRepository
                .findByPagination(paginationRequest.getPageRequest())
                .map(NoticeResponse::new);
    }
}
