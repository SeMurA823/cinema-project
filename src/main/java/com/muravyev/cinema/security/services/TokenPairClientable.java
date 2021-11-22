package com.muravyev.cinema.security.services;

import java.util.LinkedHashMap;
import java.util.Map;

public interface TokenPairClientable extends TokenPair{
    ClientSessionService.HttpClientSessionable<?> getClient();

    @Override
    default Map<String, Object> toResult(){
        return new LinkedHashMap<>(){{
            put("client_id", getClient().compact());
            put("access_token", getAccessToken().compact());
            put("refresh_token", getRefreshToken().compact());
            put("token_type", "bearer");
            put("expires_in", getAccessToken().getExpiryDate().getTime());
        }};
    }
}
