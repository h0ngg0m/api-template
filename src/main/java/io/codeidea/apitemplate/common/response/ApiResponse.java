package io.codeidea.apitemplate.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private T data;
    private String code;
    private String message;

    public static <T> ApiResponse<T> of(T data, String code, String message) {
        return new ApiResponse<>(data, code, message);
    }
}
