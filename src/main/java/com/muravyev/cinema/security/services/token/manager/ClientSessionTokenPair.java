package com.muravyev.cinema.security.services.token.manager;

import com.muravyev.cinema.security.services.session.ClientSessionService;
import com.muravyev.cinema.security.services.token.Token;

public class ClientSessionTokenPair implements TokenPairClientable{
    private final ClientSessionService.HttpClientSessionable<?> client;
    private final TokenPair tokenPair;

    public ClientSessionTokenPair(ClientSessionService.HttpClientSessionable<?> client, TokenPair tokenPair) {
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
