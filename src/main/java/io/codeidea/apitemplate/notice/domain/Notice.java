package io.codeidea.apitemplate.notice.domain;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Notice {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    @Setter private LocalDateTime updatedAt;

    public static Notice of(
            final Long id,
            final String title,
            final String content,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt) {
        return new Notice(id, title, content, createdAt, updatedAt);
    }

    public static Notice create(
            final NoticeCreate noticeCreate,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt) {
        return new Notice(null, noticeCreate.title(), noticeCreate.content(), createdAt, updatedAt);
    }
}
