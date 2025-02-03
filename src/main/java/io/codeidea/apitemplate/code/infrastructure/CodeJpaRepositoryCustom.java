package io.codeidea.apitemplate.code.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CodeJpaRepositoryCustom {

    Page<CodeEntity> findByPagination(Pageable pageable);
}
