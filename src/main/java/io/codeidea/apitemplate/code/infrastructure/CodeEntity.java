package io.codeidea.apitemplate.code.infrastructure;

import io.codeidea.apitemplate.code.domain.Code;
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
@Table(name = "code")
public class CodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String value;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_group_id")
    private CodeGroupEntity codeGroup;

    public Code toDomain() {
        return Code.of(id, title, value, createdAt, updatedAt, codeGroup.toDomain());
    }

    public static CodeEntity from(Code code) {
        return new CodeEntity(
                code.getId(),
                code.getTitle(),
                code.getValue(),
                code.getCreatedAt(),
                code.getUpdatedAt(),
                CodeGroupEntity.from(code.getCodeGroup()));
    }
}
