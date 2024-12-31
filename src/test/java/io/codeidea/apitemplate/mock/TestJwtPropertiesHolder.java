package io.codeidea.apitemplate.mock;

import io.codeidea.apitemplate.common.infrastructure.jwt.JwtPropertiesHolder;

public class TestJwtPropertiesHolder implements JwtPropertiesHolder {

    @Override
    public String getSecretKey() {
        return "test-a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0";
    }

    @Override
    public long getAccessTokenExpiration() {
        return 60000; // 1min
    }
}
