package io.codeidea.apitemplate.code.infrastructure;

import io.codeidea.apitemplate.code.domain.Code;
import io.codeidea.apitemplate.code.service.port.CodeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CodeRepositoryImpl implements CodeRepository {

    private final CodeJpaRepository jpaRepository;

    @Override
    public Page<Code> findByPagination(Pageable pageable) {
        return jpaRepository.findByPagination(pageable).map(CodeEntity::toDomain);
    }

    @Override
    public Code save(Code code) {
        CodeEntity entity = CodeEntity.from(code);
        jpaRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<Code> findById(Long id) {
        return jpaRepository.findById(id).map(CodeEntity::toDomain);
    }
}
