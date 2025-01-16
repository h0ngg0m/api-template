package io.codeidea.apitemplate.notice.infrastructure;

import io.codeidea.apitemplate.notice.domain.Notice;
import io.codeidea.apitemplate.notice.service.port.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepository {

    private final NoticeJpaRepository noticeJpaRepository;

    @Override
    public Notice save(Notice notice) {
        NoticeEntity entity = NoticeEntity.from(notice);
        noticeJpaRepository.save(entity);
        return entity.toDomain();
    }
}
