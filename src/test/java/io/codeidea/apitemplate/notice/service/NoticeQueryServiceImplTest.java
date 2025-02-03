package io.codeidea.apitemplate.notice.service;

import static org.assertj.core.api.Assertions.assertThat;

import io.codeidea.apitemplate.common.request.PaginationRequest;
import io.codeidea.apitemplate.mock.FakeNoticeRepository;
import io.codeidea.apitemplate.notice.domain.Notice;
import io.codeidea.apitemplate.notice.service.port.NoticeRepository;
import io.codeidea.apitemplate.notice.service.response.NoticeResponse;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

class NoticeQueryServiceImplTest {

    private NoticeQueryService noticeQueryService;
    private NoticeRepository noticeRepository;

    @BeforeEach
    void setup() {
        noticeRepository = new FakeNoticeRepository();
        noticeQueryService = new NoticeQueryServiceImpl(noticeRepository);

        noticeRepository.save(
                Notice.of(1L, "title1", "content1", LocalDateTime.now(), LocalDateTime.now()));
        noticeRepository.save(
                Notice.of(2L, "title2", "content2", LocalDateTime.now(), LocalDateTime.now()));
        noticeRepository.save(
                Notice.of(3L, "title3", "content3", LocalDateTime.now(), LocalDateTime.now()));
        noticeRepository.save(
                Notice.of(4L, "title4", "content4", LocalDateTime.now(), LocalDateTime.now()));
        noticeRepository.save(
                Notice.of(5L, "title5", "content5", LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    void 공지사항들을_페이징으로_조회할_수_있다() {
        // given
        PaginationRequest paginationRequest = new PaginationRequest(0, 2, "id", false);

        // when
        Page<NoticeResponse> notices = noticeQueryService.findByPagination(paginationRequest);

        // then
        assertThat(notices.getTotalElements()).isEqualTo(5);
        assertThat(notices.getTotalPages()).isEqualTo(3);
        assertThat(notices.getContent().size()).isEqualTo(2);
        assertThat(notices.hasNext()).isTrue();
    }

    @Test
    void 공지사항을_PK로_조회할_수_있다() {
        // given
        Long id = 1L;

        // when
        NoticeResponse notice = noticeQueryService.findById(id);

        // then
        assertThat(notice.id()).isEqualTo(id);
        assertThat(notice.title()).isEqualTo("title1");
        assertThat(notice.content()).isEqualTo("content1");
    }
}
