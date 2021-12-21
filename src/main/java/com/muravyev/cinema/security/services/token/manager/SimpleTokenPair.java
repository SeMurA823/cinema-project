package com.muravyev.cinema.security.services.token.manager;

import com.muravyev.cinema.security.services.token.Token;

public class SimpleTokenPair implements TokenPair {
    private final Token accessToken;
    private final Token refreshToken;

    public SimpleTokenPair(Token accessToken, Token refreshToken) {
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
