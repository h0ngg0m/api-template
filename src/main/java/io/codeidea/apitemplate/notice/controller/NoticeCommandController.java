package io.codeidea.apitemplate.notice.controller;

import io.codeidea.apitemplate.common.response.ApiResponse;
import io.codeidea.apitemplate.common.response.ApiResponseFactory;
import io.codeidea.apitemplate.notice.domain.NoticeCreate;
import io.codeidea.apitemplate.notice.service.NoticeCommandService;
import io.codeidea.apitemplate.notice.service.response.NoticeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeCommandController {

    private final NoticeCommandService noticeCommandService;

    @PostMapping
    public ResponseEntity<ApiResponse<NoticeResponse>> create(
            @Valid @RequestBody NoticeCreate noticeCreate) {
        return ApiResponseFactory.ok(noticeCommandService.create(noticeCreate));
    }
}
