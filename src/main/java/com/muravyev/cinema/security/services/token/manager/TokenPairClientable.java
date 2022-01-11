package com.muravyev.cinema.security.services.token.manager;

import com.muravyev.cinema.security.services.session.ClientSession;

import java.util.LinkedHashMap;
import java.util.Map;

public interface TokenPairClientable extends TokenPair {
    ClientSession getClient();

    @Override
    default Map<String, Object> result() {
        return new LinkedHashMap<>() {{
            put("clientId", getClient().compact());
            put("accessToken", getAccessToken().compact());
            put("refreshToken", getRefreshToken().compact());
            put("tokenType", "bearer");
            put("expiresIn", getAccessToken().getExpirationDate().getTime());
        }};
    }
}
