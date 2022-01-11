package com.muravyev.cinema.security.services.token.cookieConfigurator;

import com.muravyev.cinema.security.services.token.Token;
import org.springframework.http.ResponseCookie;

import java.time.Duration;

public interface CookieConfigurator<T> {


    String configureSession(T t);

    String configure(T t);
}
