package io.codeidea.apitemplate.mock;

import io.codeidea.apitemplate.code.domain.Code;
import io.codeidea.apitemplate.code.service.port.CodeRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class FakeCodeRepository implements CodeRepository {

    private Long id = 1L;
    private final List<Code> codes = new ArrayList<>();

    @Override
    public Optional<Code> findById(Long id) {
        return codes.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    @Override
    public Code save(Code code) {
        if (code.getId() == null) {
            Code newCode =
                    Code.of(
                            id,
                            code.getTitle(),
                            code.getValue(),
                            code.getCreatedAt(),
                            code.getUpdatedAt(),
                            code.getCodeGroup());

            codes.add(newCode);
            id++;
            return newCode;
        } else {
            codes.removeIf(a -> a.getId().equals(code.getId()));
            codes.add(code);
            return code;
        }
    }

    @Override
    public Page<Code> findByPagination(Pageable pageable) {
        List<Sort.Order> orders = pageable.getSort().get().toList();
        if (orders.size() != 1) {
            throw new RuntimeException("Sort should be only one");
        } else {
            Sort.Order order = orders.get(0);
            boolean desc = order.getDirection().equals(Sort.Direction.DESC);
            String orderBy = order.getProperty();

            Stream<Code> sortedCodes = sortCodes(orderBy, desc, codes.stream());

            return new PageImpl<>(
                    sortedCodes.skip(pageable.getOffset()).limit(pageable.getPageSize()).toList(),
                    pageable,
                    codes.size());
        }
    }

    private static Stream<Code> sortCodes(String orderBy, boolean desc, Stream<Code> targetCodes) {
        switch (orderBy) {
            case "id" -> targetCodes =
                    desc
                            ? targetCodes.sorted(Comparator.comparing(Code::getId).reversed())
                            : targetCodes.sorted(Comparator.comparing(Code::getId));
            case "title" -> targetCodes =
                    desc
                            ? targetCodes.sorted(Comparator.comparing(Code::getTitle).reversed())
                            : targetCodes.sorted(Comparator.comparing(Code::getTitle));
            case "value" -> targetCodes =
                    desc
                            ? targetCodes.sorted(Comparator.comparing(Code::getValue).reversed())
                            : targetCodes.sorted(Comparator.comparing(Code::getValue));
            case "createdAt" -> targetCodes =
                    desc
                            ? targetCodes.sorted(
                                    Comparator.comparing(Code::getCreatedAt).reversed())
                            : targetCodes.sorted(Comparator.comparing(Code::getCreatedAt));
            case "updatedAt" -> targetCodes =
                    desc
                            ? targetCodes.sorted(
                                    Comparator.comparing(Code::getUpdatedAt).reversed())
                            : targetCodes.sorted(Comparator.comparing(Code::getUpdatedAt));
        }
        return targetCodes;
    }
}
