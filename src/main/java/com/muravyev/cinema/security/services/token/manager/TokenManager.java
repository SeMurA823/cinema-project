package com.muravyev.cinema.security.services.token.manager;

import com.muravyev.cinema.entities.users.User;

public interface TokenManager {
    TokenPairClientable createTokenClientSession(User user);

    TokenPair refresh(String refreshToken);

    void disable(String accessTokenStr);

    void disableAll(User user);
}
