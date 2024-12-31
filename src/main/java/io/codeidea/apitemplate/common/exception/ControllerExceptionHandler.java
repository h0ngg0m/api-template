package io.codeidea.apitemplate.common.exception;

import io.codeidea.apitemplate.common.exception.custom.CustomException;
import io.codeidea.apitemplate.common.exception.custom.UnauthorizedException;
import io.codeidea.apitemplate.common.response.ApiResponse;
import io.codeidea.apitemplate.common.response.ApiResponseFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiResponse<?>> handleIllegalArgumentException(
            IllegalArgumentException e) {
        log.error("handleIllegalArgumentException catch IllegalArgumentException!: ", e);
        return ApiResponseFactory.badRequest();
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e) {
        log.error("handleCustomException catch CustomException!: ", e);
        return ApiResponseFactory.badRequest(e.getResponseCode());
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ApiResponse<?>> handleUnauthorizedException(UnauthorizedException e) {
        log.error("handleUnauthorizedException catch UnauthorizedException!: ", e);
        return ApiResponseFactory.unauthorized(e.getResponseCode());
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("handleException catch Exception!: ", e);
        return ApiResponseFactory.serverError();
    }
}
