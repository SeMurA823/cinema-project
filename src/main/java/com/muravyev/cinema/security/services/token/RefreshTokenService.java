package com.muravyev.cinema.security.services.token;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.session.ClientSession;
import com.muravyev.cinema.entities.session.RefreshToken;
import com.muravyev.cinema.repo.RefreshTokenRepository;
import com.muravyev.cinema.security.exceptions.InvalidTokenException;
import com.muravyev.cinema.security.services.session.ClientSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class RefreshTokenService implements TokenService<ClientSessionService.HttpClientSessionable<ClientSession>> {
    @Value("${token.refresh.age}")
    private long maxAge;
    @Value("${token.refresh.cookie}")
    private String cookieName;
    @Value("${app.cookie.path}")
    private String cookiePath;
    @Value("${app.cookie.domain}")
    private String cookieDomain;
    @Autowired
    private RefreshTokenRepository tokenRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Token createToken(ClientSessionService.HttpClientSessionable<ClientSession> httpClientSessionable) {
        ClientSession clientSession = httpClientSessionable.get();
        tokenRepository.setStatusAllByClientSession(clientSession, EntityStatus.NOT_ACTIVE);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(generateTokenStr(clientSession.getUser().getUsername()));
        refreshToken.setExpiryDate(generateExpiryDate());
        refreshToken.setClientSession(clientSession);
        return new RefreshTokenImpl(tokenRepository.save(refreshToken));
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Token refreshToken(String token) {
        Optional<RefreshToken> optionalRefreshToken =
                tokenRepository.findByTokenAndEntityStatus(token, EntityStatus.ACTIVE);
        if (optionalRefreshToken.isEmpty())
            throw new InvalidTokenException();
        RefreshToken oldRefreshToken = optionalRefreshToken.get();
        tokenRepository.setStatusAllByClientSession(oldRefreshToken.getClientSession(), EntityStatus.NOT_ACTIVE);
        if (oldRefreshToken.getClientSession().getEntityStatus() == EntityStatus.NOT_ACTIVE) {
            throw new InvalidTokenException();
        }
        RefreshToken refreshToken = refreshToken(oldRefreshToken);
        disableToken(oldRefreshToken);
        return new RefreshTokenImpl(tokenRepository.save(refreshToken));
    }

    @Override
    public String extractUsername(String token) {
        String s = new String(Base64.getDecoder().decode(token.getBytes(StandardCharsets.UTF_8)));
        return s.split(":")[1];
    }

    @Override
    public Date extractExpiryDate(String token) {
        return tokenRepository.findByTokenAndEntityStatus(token, EntityStatus.ACTIVE)
                .orElseThrow(InvalidTokenException::new).getExpiryDate();
    }

    private RefreshToken refreshToken(RefreshToken refreshToken) {
        RefreshToken newRefreshToken = new RefreshToken();
        String username = extractUsername(refreshToken.getToken());
        newRefreshToken.setToken(generateTokenStr(username));
        newRefreshToken.setClientSession(refreshToken.getClientSession());
        newRefreshToken.setExpiryDate(generateExpiryDate());
        return newRefreshToken;
    }

    @Override
    public Token disableToken(String token) {
        RefreshToken refreshToken = tokenRepository.findByTokenAndEntityStatus(token, EntityStatus.ACTIVE)
                .orElseThrow(InvalidTokenException::new);
        refreshToken.setEntityStatus(EntityStatus.NOT_ACTIVE);
        return new RefreshTokenImpl(tokenRepository.save(refreshToken));
    }

    private Date generateExpiryDate() {
        return new Date(new Date().getTime() + maxAge);
    }

    private String generateTokenStr(String username) {
        String uuid = UUID.randomUUID().toString()
                .replace("-", "");
        return new String(Base64.getEncoder().encode((uuid + ':' + username).getBytes(StandardCharsets.UTF_8)));
    }

    private RefreshToken disableToken(RefreshToken refreshToken) {
        refreshToken.setEntityStatus(EntityStatus.NOT_ACTIVE);
        return refreshToken;
    }


    private class RefreshTokenImpl implements Token {
        private final RefreshToken refreshToken;

        public RefreshTokenImpl(RefreshToken refreshToken) {
            this.refreshToken = refreshToken;
        }

        @Override
        public String compact() {
            return refreshToken.getToken();
        }

        @Override
        public ResponseCookie toCookie() {
            return toCookie(maxAge);
        }

        @Override
        public ResponseCookie toCookie(long maxAge) {
            return ResponseCookie.from(cookieName, refreshToken.getToken())
                    .maxAge(maxAge)
                    .httpOnly(true)
                    .path(cookiePath)
                    .sameSite("LAX")
                    .domain(cookieDomain)
                    .build();
        }

        @Override
        public Date getExpiryDate() {
            return refreshToken.getExpiryDate();
        }

        @Override
        public Map<String, Object> result() {
            return new LinkedHashMap<>() {{
                put("refresh_token", refreshToken.getToken());
                put("expires_in", refreshToken.getExpiryDate().getTime());
            }};
        }
    }
}
