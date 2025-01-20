package io.codeidea.apitemplate.notice.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class NoticeJpaRepositoryCustomImpl implements NoticeJpaRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private static final QNoticeEntity notice = QNoticeEntity.noticeEntity;

    @Override
    public Page<NoticeEntity> findByPagination(Pageable pageable) {

        final BooleanBuilder builder = new BooleanBuilder();

        List<NoticeEntity> content =
                queryFactory
                        .selectFrom(notice)
                        .where(builder)
                        .orderBy(notice.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                () ->
                        queryFactory
                                .select(notice.id.count())
                                .from(notice)
                                .where(builder)
                                .fetchOne());
    }
}
