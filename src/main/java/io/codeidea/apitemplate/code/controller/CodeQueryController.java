package io.codeidea.apitemplate.code.controller;

import io.codeidea.apitemplate.code.service.CodeQueryService;
import io.codeidea.apitemplate.code.service.response.CodeResponse;
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
@RequestMapping("/api/v1/codes")
@RequiredArgsConstructor
public class CodeQueryController {

    private final CodeQueryService codeQueryService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CodeResponse>>> findByPagination(
            @Valid PaginationRequest paginationRequest) {
        return ApiResponseFactory.ok(codeQueryService.findByPagination(paginationRequest));
    }
}
