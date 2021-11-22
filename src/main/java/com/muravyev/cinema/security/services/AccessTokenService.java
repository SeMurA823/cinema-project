package com.muravyev.cinema.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Component
public class AccessTokenService implements TokenService<UserDetails> {
    @Value("${token.access.age}")
    private long maxAge;

    @Value("${token.access.cookie}")
    private String cookieKey;

    @Value("${token.access.secret}")
    private String secretKey;

    @Value("${app.cookie.path}")
    private String cookiePath;


    private Key getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Token createToken(UserDetails user) {
        return generateToken(user.getUsername());
    }

    @Override
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    @Override
    public Date extractExpiryDate(String token) {
        return extractAllClaims(token).getExpiration();
    }

    @Override
    public Token disableToken(String token) {
        return new AccessTokenImpl(
                Jwts.builder()
                        .setExpiration(new Date())
                        .setClaims(new HashMap<>())
                        .setSubject("")
                        .signWith(getKey(), SignatureAlgorithm.HS256)
                        .compact());
    }

    @Override
    public Token refreshToken(String token) {
        return generateToken(extractUsername(token));
    }

    private Token generateToken(String username) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Date now = new Date();
        Date expiration = new Date(now.getTime() + this.maxAge);
        String compact = Jwts.builder()
                .setId(id)
                .setClaims(new HashMap<>())
                .setSubject(username)
                .setExpiration(expiration)
                .setIssuedAt(new Date())
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return new AccessTokenImpl(compact);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private class AccessTokenImpl implements Token {
        private final String token;
        private final ResponseCookie responseCookie ;
        private final LinkedHashMap<String, Object> result;
        private final Date expiryDate;


        public AccessTokenImpl(String token) {
            this.token = token;
            this.expiryDate = extractExpiryDate(token);
            result = new LinkedHashMap<>() {{
                put("access_token", token);
                put("expires_in", getExpiryDate().getTime());
                put("token_type", "bearer");
            }};
            responseCookie = toCookie(maxAge);
        }

        @Override
        public String compact() {
            return token;
        }

        @Override
        public ResponseCookie toCookie() {
            return responseCookie;
        }

        @Override
        public ResponseCookie toCookie(long maxAge) {
            return ResponseCookie.from(AccessTokenService.this.cookieKey, token)
                    .maxAge(maxAge)
                    .httpOnly(true)
                    .path(cookiePath)
                    .build();
        }

        @Override
        public Date getExpiryDate() {
            return expiryDate;
        }

        @Override
        public Map<String, Object> result() {
            return result;
        }
    }


}
