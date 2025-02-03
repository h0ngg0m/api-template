package io.codeidea.apitemplate.notice.controller;

import io.codeidea.apitemplate.common.response.ApiResponse;
import io.codeidea.apitemplate.common.response.ApiResponseFactory;
import io.codeidea.apitemplate.notice.domain.NoticeCreate;
import io.codeidea.apitemplate.notice.service.NoticeCommandService;
import io.codeidea.apitemplate.notice.service.response.NoticeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeCommandController {

    private final NoticeCommandService noticeCommandService;

    @PostMapping
    @PreAuthorize("hasAuthority('SUPER')")
    public ResponseEntity<ApiResponse<NoticeResponse>> create(
            @Valid @RequestBody NoticeCreate noticeCreate) {
        return ApiResponseFactory.created(noticeCommandService.create(noticeCreate));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        noticeCommandService.delete(id);
        return ApiResponseFactory.noContent();
    }
}
