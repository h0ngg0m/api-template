package io.codeidea.apitemplate.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseFactory {

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.of(
                                data,
                                ApiResponseCode.SUCCESS.getCode(),
                                ApiResponseCode.SUCCESS.getMessage()));
    }

    public static ResponseEntity<ApiResponse<Void>> ok() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.of(
                                null,
                                ApiResponseCode.SUCCESS.getCode(),
                                ApiResponseCode.SUCCESS.getMessage()));
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.of(
                                data,
                                ApiResponseCode.SUCCESS.getCode(),
                                ApiResponseCode.SUCCESS.getMessage()));
    }

    public static ResponseEntity<ApiResponse<Void>> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(
                        ApiResponse.of(
                                null,
                                ApiResponseCode.SUCCESS.getCode(),
                                ApiResponseCode.SUCCESS.getMessage()));
    }

    public static ResponseEntity<ApiResponse<?>> badRequest() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse.of(
                                null,
                                ApiResponseCode.BAD_REQUEST.getCode(),
                                ApiResponseCode.BAD_REQUEST.getMessage()));
    }

    public static ResponseEntity<ApiResponse<?>> badRequest(ApiResponseCode responseCode) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.of(null, responseCode.getCode(), responseCode.getMessage()));
    }

    public static ResponseEntity<ApiResponse<?>> unauthorized(ApiResponseCode responseCode) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.of(null, responseCode.getCode(), responseCode.getMessage()));
    }

    public static ResponseEntity<ApiResponse<?>> serverError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ApiResponse.of(
                                null,
                                ApiResponseCode.SERVER_ERROR.getCode(),
                                ApiResponseCode.SERVER_ERROR.getMessage()));
    }
}
