package com.muravyev.cinema.security.services;

import java.util.Date;

/**
 * Token service for create, refresh and disable a token
 * @param <T>, Class whose objects will be used to generate the token
 */
public interface TokenService<T> {
    /**
     *
     * @param t, The object that will be used to generate the token
     * @return new token
     */
    Token createToken(T t);

    /**
     * Refresh token
     * @param token, Token to be refreshed
     * @return new token
     */
    Token refreshToken(String token);

    String extractUsername(String token);

    Date extractExpiryDate(String token);

    /**
     * Disable token
     * @param token, token to be disabled
     * @return token with age = 0
     */
    Token disableToken(String token);
}
