package com.muravyev.cinema.security.services.token.manager;

import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.security.services.session.Client;
import com.muravyev.cinema.security.services.session.ClientService;
import com.muravyev.cinema.security.services.token.AccessTokenService;
import com.muravyev.cinema.security.services.token.RefreshTokenService;
import com.muravyev.cinema.security.services.token.Token;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenManagerImpl implements TokenManager {
    private final RefreshTokenService refreshTokenService;
    private final AccessTokenService accessTokenService;
    private final ClientService clientService;

    public TokenManagerImpl(RefreshTokenService refreshTokenService,
                            AccessTokenService accessTokenService,
                            ClientService clientService) {
        this.refreshTokenService = refreshTokenService;
        this.accessTokenService = accessTokenService;
        this.clientService = clientService;
    }

    @Override
    @Transactional
    public TokenPairClientable createTokenClientSession(User user) {
        Client session = clientService.createSession(user);
        Token refreshToken = refreshTokenService.createToken(session);
        Token accessToken = accessTokenService.createToken(session);
        return new ClientTokenPair(session, new SimpleTokenPair(accessToken, refreshToken));
    }

    @Override
    public TokenPair refresh(String refreshToken) {
        Token newRefreshToken = refreshTokenService.refreshToken(refreshToken);
        Token newAccessToken = accessTokenService.refreshToken(newRefreshToken.getSubject());
        return new SimpleTokenPair(newAccessToken, newRefreshToken);
    }

    @Override
    public void disable(String accessTokenStr) {
        clientService.disableClient(accessTokenService.extractSubject(accessTokenStr));
    }

    @Override
    public void disableAll(User user) {
        clientService.disableAll(user);
    }

}
