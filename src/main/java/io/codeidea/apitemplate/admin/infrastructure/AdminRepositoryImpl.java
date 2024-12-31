package io.codeidea.apitemplate.admin.infrastructure;

import io.codeidea.apitemplate.admin.domain.Admin;
import io.codeidea.apitemplate.admin.service.port.AdminRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepository {

    private final AdminJpaRepository jpaRepository;

    @Override
    public Optional<Admin> findByLoginId(String loginId) {
        return jpaRepository.findByLoginId(loginId).map(AdminEntity::toDomain);
    }

    @Override
    public boolean existsByLoginId(String loginId) {
        return jpaRepository.existsByLoginId(loginId);
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return jpaRepository.findById(id).map(AdminEntity::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Admin save(Admin admin) {
        AdminEntity entity = AdminEntity.from(admin);
        jpaRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Page<Admin> findByPagination(Pageable pageable) {
        return jpaRepository.findByPagination(pageable).map(AdminEntity::toDomain);
    }
}
