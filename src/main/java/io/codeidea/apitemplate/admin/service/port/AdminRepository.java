package io.codeidea.apitemplate.admin.service.port;

import io.codeidea.apitemplate.admin.domain.Admin;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminRepository {

    Optional<Admin> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);

    Optional<Admin> findById(Long id);

    void deleteById(Long id);

    Admin save(Admin admin);

    Page<Admin> findByPagination(Pageable pageable);
}
