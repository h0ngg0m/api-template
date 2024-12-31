package io.codeidea.apitemplate.admin.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminJpaRepositoryCustom {

    Page<AdminEntity> findByPagination(Pageable pageable);
}
