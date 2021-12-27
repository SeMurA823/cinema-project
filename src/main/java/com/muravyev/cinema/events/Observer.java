package com.muravyev.cinema.events;

import com.muravyev.cinema.events.NotificationManager;

public interface Observer<T> {
    void notify(T event);
    void setNotificationManager(NotificationManager<T> notificationManager);
}
