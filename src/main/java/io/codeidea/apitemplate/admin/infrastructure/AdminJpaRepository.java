package io.codeidea.apitemplate.admin.infrastructure;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminJpaRepository
        extends JpaRepository<AdminEntity, Long>, AdminJpaRepositoryCustom {
    Optional<AdminEntity> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
}
