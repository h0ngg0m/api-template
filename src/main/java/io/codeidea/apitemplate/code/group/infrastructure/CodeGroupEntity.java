package io.codeidea.apitemplate.code.group.infrastructure;

import io.codeidea.apitemplate.code.group.domain.CodeGroup;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "code_group")
public class CodeGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CodeGroup toDomain() {
        return CodeGroup.of(id, title, createdAt, updatedAt);
    }

    public static CodeGroupEntity from(CodeGroup codeGroup) {
        return new CodeGroupEntity(
                codeGroup.getId(),
                codeGroup.getTitle(),
                codeGroup.getCreatedAt(),
                codeGroup.getUpdatedAt());
    }
}
