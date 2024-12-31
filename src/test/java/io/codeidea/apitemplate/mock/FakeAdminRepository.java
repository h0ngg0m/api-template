package io.codeidea.apitemplate.mock;

import io.codeidea.apitemplate.admin.domain.Admin;
import io.codeidea.apitemplate.admin.service.port.AdminRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class FakeAdminRepository implements AdminRepository {

    private Long id = 1L;
    private final List<Admin> admins = new ArrayList<>();

    @Override
    public Optional<Admin> findByLoginId(String loginId) {
        return admins.stream().filter(a -> a.getLoginId().equals(loginId)).findFirst();
    }

    @Override
    public boolean existsByLoginId(String loginId) {
        return admins.stream().anyMatch(a -> a.getLoginId().equals(loginId));
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return admins.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    @Override
    public void deleteById(Long id) {
        admins.removeIf(a -> a.getId().equals(id));
    }

    @Override
    public Admin save(Admin admin) {
        if (admin.getId() == null) {
            Admin newAdmin =
                    Admin.create(
                            id,
                            admin.getName(),
                            admin.getLoginId(),
                            admin.getPassword(),
                            admin.getRole(),
                            admin.getCreatedAt(),
                            admin.getUpdatedAt(),
                            admin.getLastLoginAt());

            admins.add(newAdmin);
            id++;
            return newAdmin;
        } else {
            admins.removeIf(a -> a.getId().equals(admin.getId()));
            admins.add(admin);
            return admin;
        }
    }

    @Override
    public Page<Admin> findByPagination(Pageable pageable) {
        List<Sort.Order> orders = pageable.getSort().get().toList();
        if (orders.size() != 1) {
            throw new RuntimeException("Sort should be only one");
        } else {
            Sort.Order order = orders.get(0);
            boolean desc = order.getDirection().equals(Sort.Direction.DESC);
            String orderBy = order.getProperty();

            Stream<Admin> sortedAdmins = sortAdmins(orderBy, desc, admins.stream());

            return new PageImpl<>(
                    sortedAdmins.skip(pageable.getOffset()).limit(pageable.getPageSize()).toList(),
                    pageable,
                    admins.size());
        }
    }

    private static Stream<Admin> sortAdmins(
            String orderBy, boolean desc, Stream<Admin> targetAdmins) {
        switch (orderBy) {
            case "id" -> targetAdmins =
                    desc
                            ? targetAdmins.sorted(Comparator.comparing(Admin::getId).reversed())
                            : targetAdmins.sorted(Comparator.comparing(Admin::getId));
            case "name" -> targetAdmins =
                    desc
                            ? targetAdmins.sorted(Comparator.comparing(Admin::getName).reversed())
                            : targetAdmins.sorted(Comparator.comparing(Admin::getName));
            case "loginId" -> targetAdmins =
                    desc
                            ? targetAdmins.sorted(
                                    Comparator.comparing(Admin::getLoginId).reversed())
                            : targetAdmins.sorted(Comparator.comparing(Admin::getLoginId));
            case "role" -> targetAdmins =
                    desc
                            ? targetAdmins.sorted(Comparator.comparing(Admin::getRole).reversed())
                            : targetAdmins.sorted(Comparator.comparing(Admin::getRole));
            case "createdAt" -> targetAdmins =
                    desc
                            ? targetAdmins.sorted(
                                    Comparator.comparing(Admin::getCreatedAt).reversed())
                            : targetAdmins.sorted(Comparator.comparing(Admin::getCreatedAt));
            case "updatedAt" -> targetAdmins =
                    desc
                            ? targetAdmins.sorted(
                                    Comparator.comparing(Admin::getUpdatedAt).reversed())
                            : targetAdmins.sorted(Comparator.comparing(Admin::getUpdatedAt));
            case "lastLoginAt" -> targetAdmins =
                    desc
                            ? targetAdmins.sorted(
                                    Comparator.comparing(Admin::getLastLoginAt).reversed())
                            : targetAdmins.sorted(Comparator.comparing(Admin::getLastLoginAt));
        }
        return targetAdmins;
    }
}
