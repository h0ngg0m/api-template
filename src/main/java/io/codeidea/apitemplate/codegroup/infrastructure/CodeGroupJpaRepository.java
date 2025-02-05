package io.codeidea.apitemplate.codegroup.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeGroupJpaRepository
        extends JpaRepository<CodeGroupEntity, Long>, CodeGroupJpaRepositoryCustom {}
