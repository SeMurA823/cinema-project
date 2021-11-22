package com.muravyev.cinema.security.services;

import com.muravyev.cinema.entities.users.User;
import org.springframework.http.HttpCookie;

public interface ClientSessionService<T> {
    HttpClientSessionable<T> createSession(User user);

    void disableClient(String clientId);

    void disableAll(User user);

    interface HttpClientSessionable<T> {
        HttpCookie toCookie(long maxAge);
        String compact();
        T get();
    }
}
