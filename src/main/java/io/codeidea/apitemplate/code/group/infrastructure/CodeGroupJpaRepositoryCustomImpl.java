package io.codeidea.apitemplate.code.group.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class CodeGroupJpaRepositoryCustomImpl implements CodeGroupJpaRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private static final QCodeGroupEntity codeGroup = QCodeGroupEntity.codeGroupEntity;

    @Override
    public Page<CodeGroupEntity> findByPagination(Pageable pageable) {
        final BooleanBuilder builder = new BooleanBuilder();

        List<CodeGroupEntity> content =
                queryFactory
                        .selectFrom(codeGroup)
                        .where(builder)
                        .orderBy(codeGroup.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                () ->
                        queryFactory
                                .select(codeGroup.id.count())
                                .from(codeGroup)
                                .where(builder)
                                .fetchOne());
    }
}
