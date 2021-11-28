package com.muravyev.cinema.security.services;

import com.muravyev.cinema.entities.session.ClientSession;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenManagerImpl implements TokenManager {
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private ClientSessionService<ClientSession> clientSessionService;

    @Autowired
    private UserService userService;

    @Override
    public TokenPairClientable createToken(User user) {
        ClientSessionService.HttpClientSessionable<ClientSession> session = clientSessionService.createSession(user);
        Token refreshToken = refreshTokenService.createToken(session);
        Token accessToken = accessTokenService.createToken(user);
        return new InnerTokenPairClient(session, new InnerTokenPair(accessToken, refreshToken));
    }

    @Override
    public TokenPair refresh(String refreshToken) {
        Token newRefreshToken = refreshTokenService.refreshToken(refreshToken);
        String username = refreshTokenService.extractUsername(refreshToken);
        Token newAccessToken = accessTokenService.createToken(userService.loadUserByUsername(username));
        return new InnerTokenPair(newAccessToken, newRefreshToken);
    }

    @Override
    public TokenPair disable(String refreshTokenStr, String clientId) {
        clientSessionService.disableClient(clientId);
        Token refreshToken = refreshTokenService.disableToken(refreshTokenStr);
        Token accessToken = accessTokenService.disableToken("");
        return new InnerTokenPair(accessToken, refreshToken);
    }

    @Override
    public void disableAll(User user) {
        clientSessionService.disableAll(user);
    }


    private static class InnerTokenPairClient implements TokenPairClientable {

        private final ClientSessionService.HttpClientSessionable<?> client;
        private final TokenPair tokenPair;

        public InnerTokenPairClient(ClientSessionService.HttpClientSessionable<?> client, TokenPair tokenPair) {
            this.client = client;
            this.tokenPair = tokenPair;
        }

        @Override
        public Token getAccessToken() {
            return tokenPair.getAccessToken();
        }

        @Override
        public Token getRefreshToken() {
            return tokenPair.getRefreshToken();
        }

        @Override
        public ClientSessionService.HttpClientSessionable<?> getClient() {
            return client;
        }
    }

    private static class InnerTokenPair implements TokenPair {
        private final Token accessToken;
        private final Token refreshToken;

        public InnerTokenPair(Token accessToken, Token refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        @Override
        public Token getAccessToken() {
            return accessToken;
        }

        @Override
        public Token getRefreshToken() {
            return refreshToken;
        }
    }
}
