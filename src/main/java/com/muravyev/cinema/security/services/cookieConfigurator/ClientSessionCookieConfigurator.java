package com.muravyev.cinema.security.services.cookieConfigurator;

import com.muravyev.cinema.security.services.session.ClientSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class ClientSessionCookieConfigurator implements CookieConfigurator<ClientSession> {

    @Value("${session.client.cookie}")
    private String cookieName;

    @Value("${app.cookie.path}")
    private String cookiePath;

    @Value("${app.cookie.domain}")
    private String cookieDomain;

    @Override
    public String configureSessionCookie(ClientSession clientSession) {
        return ResponseCookie.from(cookieName, clientSession.compact())
                .maxAge(-1)
                .httpOnly(true)
                .path(cookiePath)
                .sameSite("LAX")
                .domain(cookieDomain)
                .build()
                .toString();
    }

    @Override
    public String configure(ClientSession clientSession) {
        return ResponseCookie.from(cookieName, clientSession.compact())
                .maxAge(Integer.MAX_VALUE)
                .httpOnly(true)
                .path(cookiePath)
                .sameSite("LAX")
                .domain(cookieDomain)
                .build()
                .toString();
    }
}
