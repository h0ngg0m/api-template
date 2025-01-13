package io.codeidea.apitemplate.admin.controller;

import io.codeidea.apitemplate.admin.service.AdminQueryService;
import io.codeidea.apitemplate.admin.service.response.AdminResponse;
import io.codeidea.apitemplate.common.request.PaginationRequest;
import io.codeidea.apitemplate.common.response.ApiResponse;
import io.codeidea.apitemplate.common.response.ApiResponseFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminQueryController {

    private final AdminQueryService adminQueryService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminResponse>>> findByPagination(
            @Valid PaginationRequest paginationRequest) {
        return ApiResponseFactory.ok(adminQueryService.findByPagination(paginationRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AdminResponse>> findById(@PathVariable Long id) {
        return ApiResponseFactory.ok(adminQueryService.findById(id));
    }
}
