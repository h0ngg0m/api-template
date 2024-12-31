package io.codeidea.apitemplate.common.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import java.util.Map;

public interface JwtProvider {

    String generateAccessToken(Map<String, Object> claims);

    boolean isTokenValid(String token);

    boolean isNotTokenValid(String token);

    Jws<Claims> getClaims(String token);
}
