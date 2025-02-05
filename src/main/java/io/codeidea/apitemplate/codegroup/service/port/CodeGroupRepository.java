package io.codeidea.apitemplate.codegroup.service.port;

import io.codeidea.apitemplate.codegroup.domain.CodeGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CodeGroupRepository {

    Page<CodeGroup> findByPagination(Pageable pageable);

    CodeGroup save(CodeGroup codeGroup);
}
