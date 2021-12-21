package com.muravyev.cinema.security.services.session;

import com.muravyev.cinema.entities.users.User;
import org.springframework.http.ResponseCookie;

public interface ClientSessionService<T> {
    HttpClientSessionable<T> createSession(User user);

    void disableClient(String clientId);

    void disableAll(User user);

    interface HttpClientSessionable<T> {
        ResponseCookie toCookie(long maxAge);

        String compact();

        T get();
    }
}
