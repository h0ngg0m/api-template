package io.codeidea.apitemplate.mock;

import io.codeidea.apitemplate.codegroup.domain.CodeGroup;
import io.codeidea.apitemplate.codegroup.service.port.CodeGroupRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class FakeCodeGroupRepository implements CodeGroupRepository {

    private Long id = 1L;
    private final List<CodeGroup> codeGroups = new ArrayList<>();

    @Override
    public CodeGroup save(CodeGroup codeGroup) {
        if (codeGroup.getId() == null) {
            CodeGroup newCodeGroup =
                    CodeGroup.of(
                            id,
                            codeGroup.getTitle(),
                            codeGroup.getCreatedAt(),
                            codeGroup.getUpdatedAt());

            codeGroups.add(newCodeGroup);
            id++;
            return newCodeGroup;
        } else {
            codeGroups.removeIf(a -> a.getId().equals(codeGroup.getId()));
            codeGroups.add(codeGroup);
            return codeGroup;
        }
    }

    @Override
    public Optional<CodeGroup> findById(Long id) {
        return codeGroups.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    @Override
    public Page<CodeGroup> findByPagination(Pageable pageable) {
        List<Sort.Order> orders = pageable.getSort().get().toList();
        if (orders.size() != 1) {
            throw new RuntimeException("Sort should be only one");
        } else {
            Sort.Order order = orders.get(0);
            boolean desc = order.getDirection().equals(Sort.Direction.DESC);
            String orderBy = order.getProperty();

            Stream<CodeGroup> sortedCodeGroups = sortCodeGroups(orderBy, desc, codeGroups.stream());

            return new PageImpl<>(
                    sortedCodeGroups
                            .skip(pageable.getOffset())
                            .limit(pageable.getPageSize())
                            .toList(),
                    pageable,
                    codeGroups.size());
        }
    }

    private static Stream<CodeGroup> sortCodeGroups(
            String orderBy, boolean desc, Stream<CodeGroup> targetCodeGroups) {
        switch (orderBy) {
            case "id" -> targetCodeGroups =
                    desc
                            ? targetCodeGroups.sorted(
                                    Comparator.comparing(CodeGroup::getId).reversed())
                            : targetCodeGroups.sorted(Comparator.comparing(CodeGroup::getId));
            case "title" -> targetCodeGroups =
                    desc
                            ? targetCodeGroups.sorted(
                                    Comparator.comparing(CodeGroup::getTitle).reversed())
                            : targetCodeGroups.sorted(Comparator.comparing(CodeGroup::getTitle));
            case "createdAt" -> targetCodeGroups =
                    desc
                            ? targetCodeGroups.sorted(
                                    Comparator.comparing(CodeGroup::getCreatedAt).reversed())
                            : targetCodeGroups.sorted(
                                    Comparator.comparing(CodeGroup::getCreatedAt));
            case "updatedAt" -> targetCodeGroups =
                    desc
                            ? targetCodeGroups.sorted(
                                    Comparator.comparing(CodeGroup::getUpdatedAt).reversed())
                            : targetCodeGroups.sorted(
                                    Comparator.comparing(CodeGroup::getUpdatedAt));
        }
        return targetCodeGroups;
    }
}
