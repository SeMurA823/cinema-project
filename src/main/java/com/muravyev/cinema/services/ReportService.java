package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.users.User;

public interface ReportService {

    void notifyUser(String message, User user);

    void notifyUser(String message, long userId);
}
