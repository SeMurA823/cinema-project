package com.muravyev.cinema.security.services.token.manager;

import com.muravyev.cinema.entities.session.ClientSession;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.security.services.session.ClientSessionService;
import com.muravyev.cinema.security.services.token.AccessTokenService;
import com.muravyev.cinema.security.services.token.RefreshTokenService;
import com.muravyev.cinema.security.services.token.Token;
import com.muravyev.cinema.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class TokenManagerImpl implements TokenManager {
    private final RefreshTokenService refreshTokenService;
    private final AccessTokenService accessTokenService;
    private final ClientSessionService<ClientSession> clientSessionService;
    private final UserService userService;

    public TokenManagerImpl(RefreshTokenService refreshTokenService, AccessTokenService accessTokenService, ClientSessionService<ClientSession> clientSessionService, UserService userService) {
        this.refreshTokenService = refreshTokenService;
        this.accessTokenService = accessTokenService;
        this.clientSessionService = clientSessionService;
        this.userService = userService;
    }

    @Override
    public TokenPairClientable createToken(User user) {
        ClientSessionService.HttpClientSessionable<ClientSession> session = clientSessionService.createSession(user);
        Token refreshToken = refreshTokenService.createToken(session);
        Token accessToken = accessTokenService.createToken(user);
        return new ClientSessionTokenPair(session, new SimpleTokenPair(accessToken, refreshToken));
    }

    @Override
    public TokenPair refresh(String refreshToken) {
        Token newRefreshToken = refreshTokenService.refreshToken(refreshToken);
        String username = refreshTokenService.extractUsername(refreshToken);
        Token newAccessToken = accessTokenService.createToken(userService.loadUserByUsername(username));
        return new SimpleTokenPair(newAccessToken, newRefreshToken);
    }

    @Override
    public TokenPair disable(String refreshTokenStr, String clientId) {
        clientSessionService.disableClient(clientId);
        Token refreshToken = refreshTokenService.disableToken(refreshTokenStr);
        Token accessToken = accessTokenService.disableToken("");
        return new SimpleTokenPair(accessToken, refreshToken);
    }

    @Override
    public void disableAll(User user) {
        clientSessionService.disableAll(user);
    }

}
