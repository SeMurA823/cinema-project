package com.muravyev.cinema.events;

import com.muravyev.cinema.events.NotificationManager;

public interface Observable<T> {
    void setNotificationManager(NotificationManager<T> notificationManager);
}
