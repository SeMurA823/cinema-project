package com.muravyev.cinema.security.services.cookieConfigurator;

import com.muravyev.cinema.security.services.token.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RefreshTokenCookieConfigurator implements CookieConfigurator<Token> {

    @Value("${token.refresh.age}")
    private long maxAgeDays;
    @Value("${token.refresh.cookie}")
    private String cookieName;
    @Value("${app.cookie.path}")
    private String cookiePath;
    @Value("${app.cookie.domain}")
    private String cookieDomain;

    @Override
    public String configureSessionCookie(Token token) {
        return ResponseCookie.from(cookieName, token.compact())
                .maxAge(-1)
                .httpOnly(true)
                .path(cookiePath)
                .sameSite("LAX")
                .domain(cookieDomain)
                .build()
                .toString();
    }

    @Override
    public String configure(Token token) {
        return ResponseCookie.from(cookieName, token.compact())
                .maxAge(Duration.ofDays(maxAgeDays))
                .httpOnly(true)
                .path(cookiePath)
                .sameSite("LAX")
                .domain(cookieDomain)
                .build()
                .toString();
    }
}
