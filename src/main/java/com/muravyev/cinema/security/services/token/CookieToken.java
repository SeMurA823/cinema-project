package com.muravyev.cinema.security.services.token;

import org.springframework.http.ResponseCookie;

import java.util.Date;
import java.util.Map;

public interface CookieToken extends Token{
    ResponseCookie toCookie();
    ResponseCookie toCookie(long maxAge);
}
