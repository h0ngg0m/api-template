package io.codeidea.apitemplate.code.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CodeUpdate(
        @NotBlank String title, @NotBlank String value, @NotNull Long codeGroupId) {}
