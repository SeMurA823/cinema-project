package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.users.User;

public interface AdminService {
    User appointAdmin(long userId);

    User demoteAdmin(long userId);
}
