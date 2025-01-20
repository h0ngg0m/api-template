package io.codeidea.apitemplate.notice.service;

import static org.assertj.core.api.Assertions.assertThat;

import io.codeidea.apitemplate.mock.FakeNoticeRepository;
import io.codeidea.apitemplate.mock.TestTimeHolder;
import io.codeidea.apitemplate.notice.domain.NoticeCreate;
import io.codeidea.apitemplate.notice.service.port.NoticeRepository;
import io.codeidea.apitemplate.notice.service.response.NoticeResponse;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NoticeCommandServiceImplTest {

    private NoticeCommandServiceImpl noticeCommandServiceImpl;
    private NoticeRepository noticeRepository;
    private LocalDateTime sampleDateTime = LocalDateTime.of(2021, 1, 1, 0, 0);

    @BeforeEach
    void setup() {
        noticeRepository = new FakeNoticeRepository();
        noticeCommandServiceImpl =
                new NoticeCommandServiceImpl(noticeRepository, new TestTimeHolder(sampleDateTime));
    }

    @Test
    void 공지사항은_생성될_수_있다() {
        // given
        NoticeCreate noticeCreate = new NoticeCreate("foo", "bar");

        // when
        NoticeResponse notice = noticeCommandServiceImpl.create(noticeCreate);

        // then
        assertThat(notice.id()).isNotNull();
        assertThat(notice.title()).isEqualTo("foo");
        assertThat(notice.content()).isEqualTo("bar");
        assertThat(notice.createdAt()).isEqualTo(sampleDateTime);
        assertThat(notice.updatedAt()).isEqualTo(sampleDateTime);
    }
}
