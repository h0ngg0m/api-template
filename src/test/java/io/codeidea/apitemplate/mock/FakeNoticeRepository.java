package io.codeidea.apitemplate.mock;

import io.codeidea.apitemplate.notice.domain.Notice;
import io.codeidea.apitemplate.notice.service.port.NoticeRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class FakeNoticeRepository implements NoticeRepository {

    private Long id = 1L;
    private final List<Notice> notices = new ArrayList<>();

    @Override
    public Notice save(Notice notice) {
        if (notice.getId() == null) {
            Notice newNotice =
                    Notice.of(
                            id,
                            notice.getTitle(),
                            notice.getContent(),
                            notice.getCreatedAt(),
                            notice.getUpdatedAt());

            notices.add(newNotice);
            id++;
            return newNotice;
        } else {
            notices.removeIf(a -> a.getId().equals(notice.getId()));
            notices.add(notice);
            return notice;
        }
    }

    @Override
    public Page<Notice> findByPagination(Pageable pageable) {
        List<Sort.Order> orders = pageable.getSort().get().toList();
        if (orders.size() != 1) {
            throw new RuntimeException("Sort should be only one");
        } else {
            Sort.Order order = orders.get(0);
            boolean desc = order.getDirection().equals(Sort.Direction.DESC);
            String orderBy = order.getProperty();

            Stream<Notice> sortedNotices = sortNotices(orderBy, desc, notices.stream());

            return new PageImpl<>(
                    sortedNotices.skip(pageable.getOffset()).limit(pageable.getPageSize()).toList(),
                    pageable,
                    notices.size());
        }
    }

    @Override
    public Optional<Notice> findById(Long id) {
        return notices.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    @Override
    public void deleteById(Long id) {
        notices.removeIf(a -> a.getId().equals(id));
    }

    private static Stream<Notice> sortNotices(
            String orderBy, boolean desc, Stream<Notice> targetNotices) {
        switch (orderBy) {
            case "id" -> targetNotices =
                    desc
                            ? targetNotices.sorted(Comparator.comparing(Notice::getId).reversed())
                            : targetNotices.sorted(Comparator.comparing(Notice::getId));
            case "title" -> targetNotices =
                    desc
                            ? targetNotices.sorted(
                                    Comparator.comparing(Notice::getTitle).reversed())
                            : targetNotices.sorted(Comparator.comparing(Notice::getTitle));
            case "content" -> targetNotices =
                    desc
                            ? targetNotices.sorted(
                                    Comparator.comparing(Notice::getContent).reversed())
                            : targetNotices.sorted(Comparator.comparing(Notice::getContent));
            case "createdAt" -> targetNotices =
                    desc
                            ? targetNotices.sorted(
                                    Comparator.comparing(Notice::getCreatedAt).reversed())
                            : targetNotices.sorted(Comparator.comparing(Notice::getCreatedAt));
            case "updatedAt" -> targetNotices =
                    desc
                            ? targetNotices.sorted(
                                    Comparator.comparing(Notice::getUpdatedAt).reversed())
                            : targetNotices.sorted(Comparator.comparing(Notice::getUpdatedAt));
        }
        return targetNotices;
    }
}
