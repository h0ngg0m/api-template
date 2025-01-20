package io.codeidea.apitemplate.notice.service.port;

import io.codeidea.apitemplate.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepository {

    Notice save(Notice notice);

    Page<Notice> findByPagination(Pageable pageable);
}
