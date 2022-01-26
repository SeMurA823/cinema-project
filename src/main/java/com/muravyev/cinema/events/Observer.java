package com.muravyev.cinema.events;

public interface Observer {
    void notify(Event<?> event, Class<? extends Event<?>> eventType);

    void setNotificationManager(NotificationManager notificationManager);
}
