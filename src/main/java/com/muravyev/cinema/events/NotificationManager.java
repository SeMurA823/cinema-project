package com.muravyev.cinema.events;

import java.util.Collection;

public interface NotificationManager {
    void subscribe(Observer observer, Collection<Class<? extends Event<?>>> eventTypes);
    void notify(Event<?> event, Class<? extends Event<?>> eventType);
}
