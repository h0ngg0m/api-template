package io.codeidea.apitemplate.notice.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeJpaRepository
        extends JpaRepository<NoticeEntity, Long>, NoticeJpaRepositoryCustom {}
