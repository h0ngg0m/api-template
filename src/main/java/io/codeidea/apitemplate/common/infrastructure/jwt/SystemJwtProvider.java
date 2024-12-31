package io.codeidea.apitemplate.common.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SystemJwtProvider implements JwtProvider {

    private final JwtPropertiesHolder jwtPropertiesHolder;

    public String generateAccessToken(Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .signWith(generateSigningKey(jwtPropertiesHolder.getSecretKey()))
                .expiration(
                        generateAccessTokenExpiration(
                                jwtPropertiesHolder.getAccessTokenExpiration()))
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    public boolean isNotTokenValid(String token) {
        return !isTokenValid(token);
    }

    public Jws<Claims> getClaims(String token) {
        if (isNotTokenValid(token)) {
            throw new JwtException("Invalid JWT token");
        }
        return parseClaims(token);
    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(generateSigningKey(jwtPropertiesHolder.getSecretKey()))
                .build()
                .parseSignedClaims(token);
    }

    private static SecretKey generateSigningKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private static Date generateAccessTokenExpiration(long accessTokenExpiration) {
        return new Date(System.currentTimeMillis() + accessTokenExpiration);
    }
}
