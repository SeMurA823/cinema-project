package com.muravyev.cinema.security.services;

import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenManagerImpl implements TokenManager {
    @Autowired
    private RefreshTokenUtils refreshTokenUtils;

    @Autowired
    private AccessTokenUtils accessTokenUtils;

    @Autowired
    private UserService userService;

    @Override
    public TokenPair createToken(User user) {
        String refreshToken = refreshTokenUtils.createToken(user);
        String accessToken = accessTokenUtils.createToken(user);
        return new InnerTokenPair(accessToken, refreshToken);
    }

    @Override
    public TokenPair refresh(String refreshToken) {
        String newRefreshToken = refreshTokenUtils.refreshToken(refreshToken);
        String username = refreshTokenUtils.extractUsername(refreshToken);
        String newAccessToken = accessTokenUtils.createToken(userService.loadUserByUsername(username));
        return new InnerTokenPair(newAccessToken, newRefreshToken);
    }


    private class InnerTokenPair implements TokenPair {
        private final String accessToken;
        private final String refreshToken;
        private final Date expiryDate;

        public InnerTokenPair(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.expiryDate = TokenManagerImpl.this.accessTokenUtils
                    .extractExpiration(this.accessToken);
        }

        @Override
        public String getAccessToken() {
            return accessToken;
        }

        @Override
        public String getRefreshToken() {
            return refreshToken;
        }

        @Override
        public Date getExpiryDate() {
            return this.expiryDate;
        }
    }
}
