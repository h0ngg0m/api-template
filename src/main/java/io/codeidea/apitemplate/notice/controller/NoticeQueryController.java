package io.codeidea.apitemplate.notice.controller;

import io.codeidea.apitemplate.common.request.PaginationRequest;
import io.codeidea.apitemplate.common.response.ApiResponse;
import io.codeidea.apitemplate.common.response.ApiResponseFactory;
import io.codeidea.apitemplate.notice.service.NoticeQueryService;
import io.codeidea.apitemplate.notice.service.response.NoticeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeQueryController {

    private final NoticeQueryService noticeQueryService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<NoticeResponse>>> findByPagination(
            @Valid PaginationRequest paginationRequest) {
        return ApiResponseFactory.ok(noticeQueryService.findByPagination(paginationRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NoticeResponse>> findById(@PathVariable Long id) {
        return ApiResponseFactory.ok(noticeQueryService.findById(id));
    }
}
