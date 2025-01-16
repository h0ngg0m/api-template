package io.codeidea.apitemplate.notice.service.port;

import io.codeidea.apitemplate.notice.domain.Notice;

public interface NoticeRepository {

    Notice save(Notice notice);
}
