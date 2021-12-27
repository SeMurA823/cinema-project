package com.muravyev.cinema.events;

public interface NotificationManager<T> {
    void subscribe(Observer<T> observer);
    void unsubscribe(Observer<T> observer);
    void notify(T event);
}
