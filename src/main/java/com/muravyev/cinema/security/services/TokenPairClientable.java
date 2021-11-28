package com.muravyev.cinema.security.services;

import java.util.LinkedHashMap;
import java.util.Map;

public interface TokenPairClientable extends TokenPair{
    ClientSessionService.HttpClientSessionable<?> getClient();

    @Override
    default Map<String, Object> result(){
        return new LinkedHashMap<>(){{
            put("clientId", getClient().compact());
            put("accessToken", getAccessToken().compact());
            put("refreshToken", getRefreshToken().compact());
            put("tokenType", "bearer");
            put("expiresIn", getAccessToken().getExpiryDate().getTime());
        }};
    }
}
