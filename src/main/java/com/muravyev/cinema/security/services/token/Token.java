package com.muravyev.cinema.security.services.token;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.ResponseCookie;

import javax.servlet.http.Cookie;
import java.util.Date;
import java.util.Map;

public interface Token {
    String compact();
    ResponseCookie toCookie();
    ResponseCookie toCookie(long maxAge);
    Date getExpiryDate();

    Map<String, Object> result();
}
