package io.codeidea.apitemplate.admin.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminUpdate(@NotBlank String name, String password, @NotNull AdminRole role) {}
