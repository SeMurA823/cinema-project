package com.muravyev.cinema.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Component
public class AccessTokenUtils {
    @Value("${token.access.expiration}")
    private Long expiration;

    @Value("${token.access.secret}")
    private String secretKey;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(UserDetails user) {
        return generateToken(user);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public boolean isExpiredToken(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String refreshToken(String token) {
        String id = UUID.randomUUID().toString();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + this.expiration);
        return Jwts.builder()
                .setId(id)
                .setClaims(new HashMap<>())
                .setSubject(extractUsername(token))
                .setExpiration(expiration)
                .setIssuedAt(new Date())
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateToken(UserDetails user) {
        String id = UUID.randomUUID().toString().replace("-", "");

        Date now = new Date();
        Date expiration = new Date(now.getTime() + this.expiration);
        return Jwts.builder()
                .setId(id)
                .setClaims(new HashMap<>())
                .setSubject(user.getUsername())
                .setExpiration(expiration)
                .setIssuedAt(new Date())
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
