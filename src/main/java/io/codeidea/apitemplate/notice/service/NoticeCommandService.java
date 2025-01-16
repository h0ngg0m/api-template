package io.codeidea.apitemplate.notice.service;

import io.codeidea.apitemplate.notice.domain.NoticeCreate;

public interface NoticeCommandService {

    void create(NoticeCreate noticeCreate);
}
