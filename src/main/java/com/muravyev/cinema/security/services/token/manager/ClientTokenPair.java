package com.muravyev.cinema.security.services.token.manager;

import com.muravyev.cinema.security.services.session.Client;
import com.muravyev.cinema.security.services.token.Token;

public class ClientTokenPair implements TokenPairClientable {
    private final Client client;
    private final TokenPair tokenPair;

    public ClientTokenPair(Client client, TokenPair tokenPair) {
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
    public Client getClient() {
        return client;
    }
}
