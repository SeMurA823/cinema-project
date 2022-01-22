package com.muravyev.cinema.security.services.token;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.session.RefreshTokenEntity;
import com.muravyev.cinema.repo.ClientSessionRepository;
import com.muravyev.cinema.repo.RefreshTokenRepository;
import com.muravyev.cinema.security.exceptions.IllegalSessionException;
import com.muravyev.cinema.security.exceptions.IllegalTokenException;
import com.muravyev.cinema.security.services.session.ClientSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

@Service
public class RefreshTokenService implements TokenService<ClientSession> {

    @Value("${token.refresh.age}")
    private long maxAgeDays;


    private final RefreshTokenRepository tokenRepository;
    private final ClientSessionRepository sessionRepository;

    public RefreshTokenService(RefreshTokenRepository tokenRepository, ClientSessionRepository sessionRepository) {
        this.tokenRepository = tokenRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Token createToken(ClientSession session) {
        tokenRepository.disableAllByClientSession(UUID.fromString(session.compact()));
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setToken(generateTokenStr(session.getSubject()));
        refreshToken.setExpiryDate(generateExpiryDate());
        refreshToken.setClientSession(sessionRepository.findByIdAndEntityStatus(UUID.fromString(session.compact()),
                        EntityStatus.ACTIVE)
                .orElseThrow(IllegalSessionException::new));
        return new RefreshToken(tokenRepository.save(refreshToken));
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Token refreshToken(String token) {
        RefreshTokenEntity oldRefreshToken = tokenRepository.findByTokenAndEntityStatus(token, EntityStatus.ACTIVE)
                .orElseThrow(IllegalTokenException::new);
        if (oldRefreshToken.getClientSession().getEntityStatus() == EntityStatus.NOT_ACTIVE) {
            throw new IllegalTokenException();
        }
        RefreshTokenEntity refreshToken = refreshToken(oldRefreshToken);
        disableToken(oldRefreshToken);
        return new RefreshToken(tokenRepository.save(refreshToken));
    }

    @Override
    public String extractUsername(String token) {
        String s = new String(Base64.getDecoder().decode(token.getBytes(StandardCharsets.UTF_8)));
        return s.split(":")[1];
    }

    @Override
    public Date extractExpirationDate(String token) {
        return tokenRepository.findByTokenAndEntityStatus(token, EntityStatus.ACTIVE)
                .orElseThrow(IllegalTokenException::new).getExpiryDate();
    }

    private RefreshTokenEntity refreshToken(RefreshTokenEntity refreshToken) {
        RefreshTokenEntity newRefreshToken = new RefreshTokenEntity();
        String username = extractUsername(refreshToken.getToken());
        newRefreshToken.setToken(generateTokenStr(username));
        newRefreshToken.setClientSession(refreshToken.getClientSession());
        newRefreshToken.setExpiryDate(generateExpiryDate());
        return newRefreshToken;
    }

    @Override
    public Token disableToken(String token) {
        RefreshTokenEntity refreshToken = tokenRepository.findByTokenAndEntityStatus(token, EntityStatus.ACTIVE)
                .orElseThrow(IllegalTokenException::new);
        return new RefreshToken(disableToken(refreshToken));
    }

    @Override
    public String extractSubject(String token) {
        return tokenRepository.findByTokenAndEntityStatus(token, EntityStatus.ACTIVE)
                .orElseThrow(IllegalTokenException::new)
                .getClientSession().getId().toString();
    }

    private RefreshTokenEntity disableToken(RefreshTokenEntity refreshToken) {
        refreshToken.setEntityStatus(EntityStatus.NOT_ACTIVE);
        return tokenRepository.save(refreshToken);
    }

    private Date generateExpiryDate() {
        return new Date(new Date().getTime() + Duration.ofDays(maxAgeDays).toMillis());
    }

    private String generateTokenStr(String username) {
        String uuid = UUID.randomUUID().toString()
                .replace("-", "");
        return new String(Base64.getEncoder().encode((uuid + ':' + username).getBytes(StandardCharsets.UTF_8)));
    }

    private class RefreshToken implements Token {
        private final Date expiration;
        private final String compact;
        private final String subject;

        public RefreshToken(Date expiration, String compact, String subject) {
            this.expiration = expiration;
            this.compact = compact;
            this.subject = subject;
        }

        public RefreshToken(RefreshTokenEntity refreshToken) {
            this.expiration = refreshToken.getExpiryDate();
            this.compact = refreshToken.getToken();
            this.subject = refreshToken.getClientSession().getId().toString();
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
                put("refreshToken", compact);
                put("expiresIn", expiration.getTime());
            }};
        }
    }

}
