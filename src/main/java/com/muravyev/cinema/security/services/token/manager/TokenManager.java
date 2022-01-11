package com.muravyev.cinema.security.services.token.manager;

import com.muravyev.cinema.entities.users.User;

public interface TokenManager {
    TokenPairClientable createTokenClientSession(User user);

    TokenPair refresh(String refreshToken);

    TokenPair disable(String refreshTokenStr, String clientId);

    void disableAll(User user);
}
