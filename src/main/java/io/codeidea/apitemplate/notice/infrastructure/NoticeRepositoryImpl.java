package io.codeidea.apitemplate.notice.infrastructure;

import io.codeidea.apitemplate.notice.domain.Notice;
import io.codeidea.apitemplate.notice.service.port.NoticeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepository {

    private final NoticeJpaRepository jpaRepository;

    @Override
    public Notice save(Notice notice) {
        NoticeEntity entity = NoticeEntity.from(notice);
        jpaRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Page<Notice> findByPagination(Pageable pageable) {
        return jpaRepository.findByPagination(pageable).map(NoticeEntity::toDomain);
    }

    @Override
    public Optional<Notice> findById(Long id) {
        return jpaRepository.findById(id).map(NoticeEntity::toDomain);
    }
}
