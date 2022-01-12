package com.muravyev.cinema.security.services.token;

import org.springframework.http.ResponseCookie;

public interface CookieToken extends Token{
    ResponseCookie toCookie();
    ResponseCookie toCookie(long maxAge);
}
