package com.muravyev.cinema.security.services;

import com.muravyev.cinema.entities.users.User;

public interface TokenManager {
    TokenPair createToken(User user);

    TokenPair refresh(String refreshToken);
}
