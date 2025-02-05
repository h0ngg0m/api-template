package io.codeidea.apitemplate.code.domain;

import io.codeidea.apitemplate.codegroup.domain.CodeGroup;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Code {

    private Long id;
    private String title;
    private String value;
    private LocalDateTime createdAt;
    @Setter private LocalDateTime updatedAt;

    private CodeGroup codeGroup;

    public static Code of(
            final Long id,
            final String title,
            final String value,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final CodeGroup group) {
        return new Code(id, title, value, createdAt, updatedAt, group);
    }
}
