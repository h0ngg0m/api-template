package io.codeidea.apitemplate.code.controller;

import io.codeidea.apitemplate.code.domain.CodeUpdate;
import io.codeidea.apitemplate.code.service.CodeCommandService;
import io.codeidea.apitemplate.code.service.response.CodeResponse;
import io.codeidea.apitemplate.common.response.ApiResponse;
import io.codeidea.apitemplate.common.response.ApiResponseFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/codes")
@RequiredArgsConstructor
public class CodeCommandController {

    private final CodeCommandService codeCommandService;

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER')")
    public ResponseEntity<ApiResponse<CodeResponse>> update(
            @PathVariable Long id, @Valid @RequestBody CodeUpdate codeUpdate) {
        return ApiResponseFactory.ok(codeCommandService.update(id, codeUpdate));
    }
}
