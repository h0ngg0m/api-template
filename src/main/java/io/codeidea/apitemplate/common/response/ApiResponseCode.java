package io.codeidea.apitemplate.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResponseCode {

    // Success
    SUCCESS("SUCCESS-001", "Success"),

    // Server error
    SERVER_ERROR("SERVER-001", "Server error"),

    // Common error
    BAD_REQUEST("COMMON-001", "Bad request"),
    UNAUTHORIZED("COMMON-002", "Unauthorized"),

    // Admin error
    ADMIN_ALREADY_EXISTS("ADMIN-001", "Admin already exists"),
    ADMIN_NOT_FOUND("ADMIN-002", "Admin not found"),

    // Notice error
    NOTICE_NOT_FOUND("NOTICE-001", "Notice not found"),

    // Code error
    CODE_NOT_FOUND("CODE-001", "Code not found"),

    // CodeGroup error
    CODE_GROUP_NOT_FOUND("CODE_GROUP-001", "Code group not found");

    private final String code;
    private String message;

    private void setMessage(String message) {
        this.message = message;
    }

    public ApiResponseCode customMessage(String message) {
        setMessage(message);
        return this;
    }
}
