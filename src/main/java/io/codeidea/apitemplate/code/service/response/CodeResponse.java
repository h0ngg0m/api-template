package io.codeidea.apitemplate.code.service.response;

import io.codeidea.apitemplate.code.domain.Code;
import io.codeidea.apitemplate.codegroup.domain.CodeGroup;
import java.time.LocalDateTime;

public record CodeResponse(
        Long id,
        String title,
        String value,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        CodeGroup codeGroup) {
    public CodeResponse(Code code) {
        this(
                code.getId(),
                code.getTitle(),
                code.getValue(),
                code.getCreatedAt(),
                code.getUpdatedAt(),
                code.getCodeGroup());
    }
}
