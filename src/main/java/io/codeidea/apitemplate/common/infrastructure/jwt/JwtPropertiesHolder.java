package io.codeidea.apitemplate.common.infrastructure.jwt;

public interface JwtPropertiesHolder {

    String getSecretKey();

    long getAccessTokenExpiration();
}
