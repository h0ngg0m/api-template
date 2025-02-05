package io.codeidea.apitemplate.codegroup.controller;

import io.codeidea.apitemplate.codegroup.service.CodeGroupQueryService;
import io.codeidea.apitemplate.codegroup.service.response.CodeGroupResponse;
import io.codeidea.apitemplate.common.request.PaginationRequest;
import io.codeidea.apitemplate.common.response.ApiResponse;
import io.codeidea.apitemplate.common.response.ApiResponseFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/code-groups")
@RequiredArgsConstructor
public class CodeGroupQueryController {

    private final CodeGroupQueryService codeGroupQueryService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CodeGroupResponse>>> findByPagination(
            @Valid PaginationRequest paginationRequest) {
        return ApiResponseFactory.ok(codeGroupQueryService.findByPagination(paginationRequest));
    }
}
