package io.codeidea.apitemplate.code.group.service.port;

import io.codeidea.apitemplate.code.group.domain.CodeGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CodeGroupRepository {

    Page<CodeGroup> findByPagination(Pageable pageable);

    CodeGroup save(CodeGroup codeGroup);
}
