package io.codeidea.apitemplate.notice.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class NoticeTest {

    @Test
    void 공지사항은_생성될_수_있다() {
        // given
        Long id = 1L;
        String title = "foo";
        String content = "bar";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        // when
        Notice notice = Notice.create(1L, title, content, createdAt, updatedAt);

        // then
        assertThat(notice.getId()).isEqualTo(1L);
        assertThat(notice.getTitle()).isEqualTo("foo");
        assertThat(notice.getContent()).isEqualTo("bar");
        assertThat(notice.getCreatedAt()).isEqualTo(createdAt);
        assertThat(notice.getUpdatedAt()).isEqualTo(updatedAt);
    }
}
