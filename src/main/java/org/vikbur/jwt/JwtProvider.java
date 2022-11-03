package org.vikbur.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vikbur.models.User;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    private final SecretKey jwtAccessSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final SecretKey jwtRefreshSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    /**
     * Время жизни AccessToken в минутах
     */
    private final int AccessTokenLifeTime = 1000;
    /**
     * Время жизни RefreshToken в днях
     */
    private final int RefreshTokenLifeTime = 30;


    public String generateAccessToken(@NonNull User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(AccessTokenLifeTime).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpirationDate = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setExpiration(accessExpirationDate)
                .signWith(jwtAccessSecret)
                .claim("roles", user.getRoles())
                .claim("name", user.getName())
                .compact();
    }

    public String generateRefreshToken(@NonNull User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(RefreshTokenLifeTime).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpirationDate = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setExpiration(refreshExpirationDate)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
