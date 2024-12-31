package io.codeidea.apitemplate.common.exception.custom;

import io.codeidea.apitemplate.common.response.ApiResponseCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ApiResponseCode responseCode;

    public CustomException(ApiResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }
}
