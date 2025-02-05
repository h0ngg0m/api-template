package io.codeidea.apitemplate.code.service.port;

import io.codeidea.apitemplate.code.domain.Code;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CodeRepository {

    Page<Code> findByPagination(Pageable pageable);

    Code save(Code code);

    Optional<Code> findById(Long id);
}
