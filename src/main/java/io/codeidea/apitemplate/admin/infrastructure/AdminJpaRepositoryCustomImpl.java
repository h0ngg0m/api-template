package io.codeidea.apitemplate.admin.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class AdminJpaRepositoryCustomImpl implements AdminJpaRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private static final QAdminEntity adminEntity = QAdminEntity.adminEntity;

    @Override
    public Page<AdminEntity> findByPagination(Pageable pageable) {

        final BooleanBuilder builder = new BooleanBuilder();

        List<AdminEntity> content =
                queryFactory
                        .selectFrom(adminEntity)
                        .where(builder)
                        .orderBy(adminEntity.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                () ->
                        queryFactory
                                .select(adminEntity.id.count())
                                .from(adminEntity)
                                .where(builder)
                                .fetchOne());
    }
}
