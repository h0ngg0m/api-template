package io.codeidea.apitemplate.codegroup.infrastructure;

import io.codeidea.apitemplate.codegroup.domain.CodeGroup;
import io.codeidea.apitemplate.codegroup.service.port.CodeGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CodeGroupRepositoryImpl implements CodeGroupRepository {

    private final CodeGroupJpaRepository jpaRepository;

    @Override
    public Page<CodeGroup> findByPagination(Pageable pageable) {
        return jpaRepository.findByPagination(pageable).map(CodeGroupEntity::toDomain);
    }

    @Override
    public CodeGroup save(CodeGroup codeGroup) {
        CodeGroupEntity entity = CodeGroupEntity.from(codeGroup);
        jpaRepository.save(entity);
        return entity.toDomain();
    }
}
