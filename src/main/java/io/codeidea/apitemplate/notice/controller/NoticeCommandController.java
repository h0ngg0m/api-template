package io.codeidea.apitemplate.notice.controller;

import io.codeidea.apitemplate.notice.service.NoticeCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeCommandController {

    private final NoticeCommandService noticeCommandService;
}
