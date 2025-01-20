package io.codeidea.apitemplate.notice.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeJpaRepositoryCustom {

    Page<NoticeEntity> findByPagination(Pageable pageable);
}
