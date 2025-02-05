package io.codeidea.apitemplate.codegroup.service;

import io.codeidea.apitemplate.codegroup.service.port.CodeGroupRepository;
import io.codeidea.apitemplate.codegroup.service.response.CodeGroupResponse;
import io.codeidea.apitemplate.common.request.PaginationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeGroupQueryServiceImpl implements CodeGroupQueryService {

    private final CodeGroupRepository codeGroupRepository;

    @Override
    public Page<CodeGroupResponse> findByPagination(PaginationRequest paginationRequest) {
        return codeGroupRepository
                .findByPagination(paginationRequest.getPageRequest())
                .map(CodeGroupResponse::new);
    }
}
