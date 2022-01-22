package com.muravyev.cinema.security.services.token.manager;

import com.muravyev.cinema.security.services.session.ClientSession;
import com.muravyev.cinema.security.services.token.Token;

public class ClientSessionTokenPair implements TokenPairClientable {
    private final ClientSession client;
    private final TokenPair tokenPair;

    public ClientSessionTokenPair(ClientSession client, TokenPair tokenPair) {
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
    public ClientSession getClient() {
        return client;
    }
}
