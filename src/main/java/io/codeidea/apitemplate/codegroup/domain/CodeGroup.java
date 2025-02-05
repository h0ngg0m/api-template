package io.codeidea.apitemplate.codegroup.domain;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeGroup {

    private Long id;
    private String title;
    private LocalDateTime createdAt;
    @Setter private LocalDateTime updatedAt;

    public static CodeGroup of(
            final Long id,
            final String title,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt) {
        return new CodeGroup(id, title, createdAt, updatedAt);
    }
}
