package io.codeidea.apitemplate.admin.controller;

import io.codeidea.apitemplate.admin.domain.AdminSignIn;
import io.codeidea.apitemplate.admin.domain.AdminSignUp;
import io.codeidea.apitemplate.admin.domain.AdminUpdate;
import io.codeidea.apitemplate.admin.service.AdminService;
import io.codeidea.apitemplate.admin.service.response.AdminResponse;
import io.codeidea.apitemplate.common.infrastructure.jwt.Jwt;
import io.codeidea.apitemplate.common.request.PaginationRequest;
import io.codeidea.apitemplate.common.response.ApiResponse;
import io.codeidea.apitemplate.common.response.ApiResponseFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminResponse>>> findByPagination(
            @Valid PaginationRequest paginationRequest) {
        return ApiResponseFactory.ok(adminService.findByPagination(paginationRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AdminResponse>> findById(@PathVariable Long id) {
        return ApiResponseFactory.ok(adminService.findById(id));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<Jwt>> signIn(@Valid @RequestBody AdminSignIn adminSignIn) {
        return ApiResponseFactory.ok(adminService.signIn(adminSignIn));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SUPER')")
    public ResponseEntity<ApiResponse<AdminResponse>> signUp(
            @Valid @RequestBody AdminSignUp adminSignUp) {
        return ApiResponseFactory.created(adminService.signUp(adminSignUp));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER')")
    public ResponseEntity<ApiResponse<AdminResponse>> updateAdmin(
            @PathVariable Long id, @Valid @RequestBody AdminUpdate adminUpdate) {
        return ApiResponseFactory.ok(adminService.update(id, adminUpdate));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER')")
    public ResponseEntity<ApiResponse<?>> deleteAdmin(@PathVariable Long id) {
        adminService.delete(id);
        return ApiResponseFactory.noContent();
    }
}
