package com.muravyev.cinema.security.services.token;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.session.ClientSessionEntity;
import com.muravyev.cinema.repo.ClientSessionRepository;
import com.muravyev.cinema.security.services.session.ClientSession;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

/**
 * Service for processing access token
 */
@Service
public class AccessTokenService implements TokenService<ClientSession> {
    @Value("${token.access.age}")
    private long maxAge;

    @Value("${token.access.cookie}")
    private String cookieKey;

    @Value("${token.access.secret}")
    private String secretKey;

    @Value("${app.cookie.domain}")
    private String cookieDomain;

    @Value("${app.cookie.path}")
    private String cookiePath;

    @Autowired
    private ClientSessionRepository sessionRepository;

    @Override
    public Token createToken(ClientSession clientSession) {
        return generateToken(clientSession.compact(), new Date(new Date().getTime() + this.maxAge));
    }

    @Override
    public Token refreshToken(String token) {
        return generateToken(extractSession(token), new Date(new Date().getTime() + this.maxAge));
    }

    @Override
    public String extractUsername(String token) {
        String sessionStr = extractAllClaims(token).getSubject();
        ClientSessionEntity session = sessionRepository.findByIdAndEntityStatus(UUID.fromString(sessionStr),
                        EntityStatus.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException("Illegal session"));
        return session.getUser().getUsername();
    }

    @Override
    public Date extractExpirationDate(String token) {
        return extractAllClaims(token).getExpiration();
    }

    @Override
    public Token disableToken(String token) {
        return generateToken(extractSession(token), new Date());
    }

    @Override
    public String extractSubject(String token) {
        return extractSession(token);
    }

    private String extractSession(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private Claims extractAllClaims(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build();
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }


    private Token generateToken(String clientSession, Date expiration) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Date now = new Date();
        String compact = Jwts.builder()
                .setId(id)
                .setClaims(new HashMap<>())
                .setSubject(clientSession)
                .setExpiration(expiration)
                .setIssuedAt(now)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return new AccessToken(compact, expiration, clientSession);
    }

    private static class AccessToken implements Token {

        private final String compact;
        private final Date expiration;
        private final String subject;

        public AccessToken(String compact, Date expiration, String subject) {
            this.compact = compact;
            this.expiration = expiration;
            this.subject = subject;
        }

        @Override
        public String compact() {
            return compact;
        }

        @Override
        public String getSubject() {
            return subject;
        }

        @Override
        public Date getExpirationDate() {
            return expiration;
        }

        @Override
        public Map<String, Object> result() {
            return new LinkedHashMap<>() {{
                put("access_token", compact);
                put("expires_in", expiration.getTime());
                put("token_type", "bearer");
            }};
        }
    }
}

