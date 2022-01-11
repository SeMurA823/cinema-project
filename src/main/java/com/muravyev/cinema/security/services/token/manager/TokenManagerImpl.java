package com.muravyev.cinema.security.services.token.manager;

import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.security.services.session.ClientSession;
import com.muravyev.cinema.security.services.session.ClientSessionService;
import com.muravyev.cinema.security.services.token.AccessTokenService;
import com.muravyev.cinema.security.services.token.RefreshTokenService;
import com.muravyev.cinema.security.services.token.Token;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenManagerImpl implements TokenManager {
    private final RefreshTokenService refreshTokenService;
    private final AccessTokenService accessTokenService;
    private final ClientSessionService clientSessionService;

    public TokenManagerImpl(RefreshTokenService refreshTokenService,
                            AccessTokenService accessTokenService,
                            ClientSessionService clientSessionService) {
        this.refreshTokenService = refreshTokenService;
        this.accessTokenService = accessTokenService;
        this.clientSessionService = clientSessionService;
    }

    @Override
    @Transactional
    public TokenPairClientable createTokenClientSession(User user) {
        ClientSession session = clientSessionService.createSession(user);
        Token refreshToken = refreshTokenService.createToken(session);
        Token accessToken = accessTokenService.createToken(session);
        return new ClientSessionTokenPair(session, new SimpleTokenPair(accessToken, refreshToken));
    }

    @Override
    public TokenPair refresh(String refreshToken) {
        Token newRefreshToken = refreshTokenService.refreshToken(refreshToken);
        Token newAccessToken = accessTokenService.refreshToken(newRefreshToken.getSubject());
        return new SimpleTokenPair(newAccessToken, newRefreshToken);
    }

    @Override
    public void disable(String accessTokenStr) {
        clientSessionService.disableClient(accessTokenService.extractSubject(accessTokenStr));
    }

    @Override
    public void disableAll(User user) {
        clientSessionService.disableAll(user);
    }

}
