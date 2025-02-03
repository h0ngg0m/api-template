package io.codeidea.apitemplate.notice.service;

import io.codeidea.apitemplate.notice.domain.NoticeCreate;
import io.codeidea.apitemplate.notice.service.response.NoticeResponse;

public interface NoticeCommandService {

    NoticeResponse create(NoticeCreate noticeCreate);

    void delete(Long id);
}
