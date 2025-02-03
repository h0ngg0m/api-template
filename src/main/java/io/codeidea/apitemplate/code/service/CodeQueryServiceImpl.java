package io.codeidea.apitemplate.code.service;

import io.codeidea.apitemplate.code.service.port.CodeRepository;
import io.codeidea.apitemplate.code.service.response.CodeResponse;
import io.codeidea.apitemplate.common.request.PaginationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeQueryServiceImpl implements CodeQueryService {

    private final CodeRepository codeRepository;

    @Override
    public Page<CodeResponse> findByPagination(PaginationRequest paginationRequest) {
        return codeRepository
                .findByPagination(paginationRequest.getPageRequest())
                .map(CodeResponse::new);
    }
}
