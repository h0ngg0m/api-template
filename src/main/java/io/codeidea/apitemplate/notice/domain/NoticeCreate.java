package io.codeidea.apitemplate.notice.domain;

import jakarta.validation.constraints.NotBlank;

public record NoticeCreate(@NotBlank String title, @NotBlank String content) {}
