package com.muravyev.cinema.security.services.session;

import com.muravyev.cinema.entities.users.User;

public interface ClientSessionService {
    ClientSession createSession(User user);

    void disableClient(String clientId);

    void disableAll(User user);
}
