package io.codeidea.apitemplate.code.group.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeGroupJpaRepository
        extends JpaRepository<CodeGroupEntity, Long>, CodeGroupJpaRepositoryCustom {}
