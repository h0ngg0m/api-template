package io.codeidea.apitemplate.code.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.codeidea.apitemplate.codegroup.domain.CodeGroup;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CodeTest {
    @Test
    void 코드의_정보를_수정할_수_있다() {
        // given
        LocalDateTime createdAt = LocalDateTime.now();
        CodeGroup codeGroup = CodeGroup.of(1L, "group1", createdAt, createdAt);
        Code code = Code.of(1L, "title", "value", createdAt, createdAt, codeGroup);

        CodeGroup newCodeGroup = CodeGroup.of(2L, "group2", createdAt, createdAt);
        LocalDateTime updatedAt = LocalDateTime.now();

        // when
        code.update(new CodeUpdate("newCode", "newName", 2L), newCodeGroup, updatedAt);

        // then
        assertThat(code.getTitle()).isEqualTo("newCode");
        assertThat(code.getValue()).isEqualTo("newName");
        assertThat(code.getCreatedAt()).isEqualTo(createdAt);
        assertThat(code.getUpdatedAt()).isEqualTo(updatedAt);
        assertThat(code.getCodeGroup()).isEqualTo(newCodeGroup);
    }
}
