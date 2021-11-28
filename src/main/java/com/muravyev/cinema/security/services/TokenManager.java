package com.muravyev.cinema.security.services;

import com.muravyev.cinema.entities.users.User;

public interface TokenManager {
    TokenPairClientable createToken(User user);

    TokenPair refresh(String refreshToken);

    TokenPair disable(String refreshTokenStr, String clientId);


    void disableAll(User user);
}
