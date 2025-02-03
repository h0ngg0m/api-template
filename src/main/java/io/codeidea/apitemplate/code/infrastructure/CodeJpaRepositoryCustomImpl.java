package io.codeidea.apitemplate.code.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class CodeJpaRepositoryCustomImpl implements CodeJpaRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private static final QCodeEntity code = QCodeEntity.codeEntity;
    private static final QCodeGroupEntity codeGroup = QCodeGroupEntity.codeGroupEntity;

    @Override
    public Page<CodeEntity> findByPagination(Pageable pageable) {
        final BooleanBuilder builder = new BooleanBuilder();

        List<CodeEntity> content =
                queryFactory
                        .selectFrom(code)
                        .leftJoin(code.codeGroup, codeGroup)
                        .fetchJoin()
                        .where(builder)
                        .orderBy(code.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        Long count =
                queryFactory
                        .select(code.count())
                        .from(code)
                        .leftJoin(code.codeGroup, codeGroup)
                        .where(builder)
                        .fetchOne();

        return PageableExecutionUtils.getPage(content, pageable, () -> count != null ? count : 0);
    }
}
