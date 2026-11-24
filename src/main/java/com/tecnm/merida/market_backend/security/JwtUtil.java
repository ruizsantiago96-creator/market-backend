package com.tecnm.merida.market_backend.security;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class JwtUtil {

    private final SecretKey signingKey;
    private final JwtParser parser;

    public JwtUtil(@Value("${auth.jwt.secret}") String secret) {
        if (secret.length() < 32) {
            throw new IllegalArgumentException("JWT_SECRET debe tener al menos 32 caracteres");
        }
        signingKey = new SecretKeySpec(secret.getBytes(UTF_8), "HmacSHA256");
        parser = Jwts.parser().verifyWith(signingKey).build();
    }

    public String generateToken(String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(1, ChronoUnit.HOURS)))
                .signWith(signingKey, Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        var jwt = parser.parseSignedClaims(token);
        if (!Jwts.SIG.HS256.getId().equals(jwt.getHeader().getAlgorithm())) {
            throw new UnsupportedJwtException("Expected an HS256 token");
        }
        return jwt.getPayload().getSubject();
    }
}
