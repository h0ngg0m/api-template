package io.codeidea.apitemplate.admin.domain;

import jakarta.validation.constraints.NotBlank;

public record AdminSignIn(@NotBlank String loginId, @NotBlank String password) {}
