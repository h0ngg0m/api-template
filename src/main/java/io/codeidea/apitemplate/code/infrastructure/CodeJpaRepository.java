package io.codeidea.apitemplate.code.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeJpaRepository
        extends JpaRepository<CodeEntity, Long>, CodeJpaRepositoryCustom {}
