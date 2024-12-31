package io.codeidea.apitemplate.common.infrastructure.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemJwtPropertiesHolder implements JwtPropertiesHolder {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration.access-token}")
    private long accessTokenExpiration;

    @Override
    public String getSecretKey() {
        return secretKey;
    }

    @Override
    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }
}
