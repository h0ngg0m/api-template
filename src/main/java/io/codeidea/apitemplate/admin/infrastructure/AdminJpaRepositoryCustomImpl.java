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
    private static final QAdminEntity admin = QAdminEntity.adminEntity;

    @Override
    public Page<AdminEntity> findByPagination(Pageable pageable) {

        final BooleanBuilder builder = new BooleanBuilder();

        List<AdminEntity> content =
                queryFactory
                        .selectFrom(admin)
                        .where(builder)
                        .orderBy(admin.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                () -> queryFactory.select(admin.id.count()).from(admin).where(builder).fetchOne());
    }
}
