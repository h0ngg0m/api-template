package io.codeidea.apitemplate.mock;

import io.codeidea.apitemplate.notice.domain.Notice;
import io.codeidea.apitemplate.notice.service.port.NoticeRepository;
import java.util.ArrayList;
import java.util.List;

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
}
