package io.codeidea.apitemplate.admin.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class AdminSignUp {

    @NotBlank private String name;
    @NotBlank private String loginId;
    @Setter @NotBlank private String password;
    @NotNull private AdminRole role;
}
