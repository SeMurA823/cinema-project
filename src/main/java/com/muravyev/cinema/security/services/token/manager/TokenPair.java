package com.muravyev.cinema.security.services.token.manager;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.muravyev.cinema.security.services.token.Token;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
public interface TokenPair {
    Token getAccessToken();
    Token getRefreshToken();
    default Map<String, Object> result(){
        return new LinkedHashMap<>(){{
            put("access_token", getAccessToken().compact());
            put("refresh_token", getRefreshToken().compact());
            put("token_type", "bearer");
            put("expires_in", getAccessToken().getExpiryDate().getTime());
        }};
    }
}