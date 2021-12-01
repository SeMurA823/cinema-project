package com.muravyev.cinema.security.services.token;

import org.springframework.http.ResponseCookie;

import java.util.Date;
import java.util.Map;

public interface Token {
    String compact();
    ResponseCookie toCookie();
    ResponseCookie toCookie(long maxAge);
    Date getExpiryDate();

    Map<String, Object> result();
}
