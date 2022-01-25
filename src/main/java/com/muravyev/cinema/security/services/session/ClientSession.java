package com.muravyev.cinema.security.services.session;

public interface ClientSession {
    String compact();

    String getSubject();
}
