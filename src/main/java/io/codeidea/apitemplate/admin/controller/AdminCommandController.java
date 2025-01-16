package io.codeidea.apitemplate.admin.controller;

import io.codeidea.apitemplate.admin.domain.AdminSignIn;
import io.codeidea.apitemplate.admin.domain.AdminSignUp;
import io.codeidea.apitemplate.admin.domain.AdminUpdate;
import io.codeidea.apitemplate.admin.service.AdminCommandService;
import io.codeidea.apitemplate.admin.service.response.AdminResponse;
import io.codeidea.apitemplate.common.infrastructure.jwt.Jwt;
import io.codeidea.apitemplate.common.response.ApiResponse;
import io.codeidea.apitemplate.common.response.ApiResponseFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminCommandController {

    private final AdminCommandService adminCommandService;

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<Jwt>> signIn(@Valid @RequestBody AdminSignIn adminSignIn) {
        return ApiResponseFactory.ok(adminCommandService.signIn(adminSignIn));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SUPER')")
    public ResponseEntity<ApiResponse<AdminResponse>> signUp(
            @Valid @RequestBody AdminSignUp adminSignUp) {
        return ApiResponseFactory.created(adminCommandService.signUp(adminSignUp));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER')")
    public ResponseEntity<ApiResponse<Void>> updateAdmin(
            @PathVariable Long id, @Valid @RequestBody AdminUpdate adminUpdate) {
        adminCommandService.update(id, adminUpdate);
        return ApiResponseFactory.ok();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER')")
    public ResponseEntity<ApiResponse<Void>> deleteAdmin(@PathVariable Long id) {
        adminCommandService.delete(id);
        return ApiResponseFactory.noContent();
    }
}
