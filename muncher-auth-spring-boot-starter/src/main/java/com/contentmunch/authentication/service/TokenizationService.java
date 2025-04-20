package com.contentmunch.authentication.service;

import com.contentmunch.authentication.config.AuthConfigProperties;
import com.contentmunch.authentication.data.MuncherUser;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenizationService {

    private final AuthConfigProperties authConfig;
    private final SecretKey secretKey;

    public TokenizationService(AuthConfigProperties authConfig) {
        this.authConfig = authConfig;
        this.secretKey = Keys.hmacShaKeyFor(authConfig.secret().getBytes());
    }

    public String generateToken(final MuncherUser user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("email", user.email())
                .claim("name", user.name())
                .claim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                        .toList())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(authConfig.maxAgeInMinutes(), ChronoUnit.MINUTES)))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(final String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {

            return false;
        }
    }

    public String extractUsername(final String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
