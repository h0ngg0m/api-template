package io.codeidea.apitemplate.codegroup.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CodeGroupJpaRepositoryCustom {

    Page<CodeGroupEntity> findByPagination(Pageable pageable);
}
