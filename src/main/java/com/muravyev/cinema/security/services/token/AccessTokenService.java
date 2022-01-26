package com.muravyev.cinema.security.services.token;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.session.ClientEntity;
import com.muravyev.cinema.repo.ClientRepository;
import com.muravyev.cinema.security.exceptions.IllegalSessionException;
import com.muravyev.cinema.security.services.session.Client;
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
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Service for processing access token
 */
@Service
public class AccessTokenService implements TokenService<Client> {
    @Value("${token.access.age}")
    private long maxAgeMinutes;

    @Value("${token.access.secret}")
    private String secretKey;

    @Autowired
    private ClientRepository sessionRepository;

    @Override
    public Token createToken(Client client) {
        return generateToken(client.compact(), createExpirationDate());
    }

    @Override
    public Token refreshToken(String clientSession) {
        return generateToken(clientSession, createExpirationDate());
    }

    @Override
    public String extractUsername(String token) {
        String sessionStr = extractAllClaims(token).getSubject();
        ClientEntity session = sessionRepository.findByIdAndEntityStatus(UUID.fromString(sessionStr),
                        EntityStatus.ACTIVE)
                .orElseThrow(IllegalSessionException::new);
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

    private Date createExpirationDate() {
        return Date.from(ZonedDateTime.now().plusMinutes(maxAgeMinutes).toInstant());
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
                put("accessToken", compact);
                put("expiresIn", expiration.getTime());
                put("tokenType", "bearer");
            }};
        }
    }
}

