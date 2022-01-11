package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.notifications.UserNotification;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    void notifyUser(String message, User user);

    void notifyUser(String message, long userId);

    void setViewedNotifications(List<Long> ids, User user);

    List<UserNotification> getNotViewedNotifications(User user);

    Page<UserNotification> getAllNotifications(User user, Pageable pageable);

    Page<UserNotification> getAllNotifications(long userId, Pageable pageable);
}
