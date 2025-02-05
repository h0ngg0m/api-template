package io.codeidea.apitemplate.codegroup.service.response;

import io.codeidea.apitemplate.codegroup.domain.CodeGroup;
import java.time.LocalDateTime;

public record CodeGroupResponse(
        Long id, String title, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public CodeGroupResponse(CodeGroup codeGroup) {
        this(
                codeGroup.getId(),
                codeGroup.getTitle(),
                codeGroup.getCreatedAt(),
                codeGroup.getUpdatedAt());
    }
}
