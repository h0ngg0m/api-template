package io.codeidea.apitemplate.common.exception.custom;

import io.codeidea.apitemplate.common.response.ApiResponseCode;

public class UnauthorizedException extends CustomException {

    public UnauthorizedException(ApiResponseCode responseCode) {
        super(responseCode);
    }
}
