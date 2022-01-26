package com.muravyev.cinema.security.services.session;

public interface Client {
    String compact();
    String getSubject();
}
