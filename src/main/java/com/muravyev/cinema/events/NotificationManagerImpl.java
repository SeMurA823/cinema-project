package com.muravyev.cinema.events;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
public class NotificationManagerImpl implements NotificationManager {

    private final Map<Class<? extends Event<?>>, Set<Observer>> observers = new HashMap<>();

    @Override
    public void subscribe(Observer observer, Collection<Class<? extends Event<?>>> eventTypes) {
        for (Class<? extends Event<?>> eventType: eventTypes) {
            observers.computeIfAbsent(eventType, x->new HashSet<>()).add(observer);
        }
    }

    @Override
    public void notify(Event<?> event, Class<? extends Event<?>> eventType) {
        log.info("Event: {} type: {}", event, eventType.getSimpleName());
        Set<Observer> observersByType = this.observers.get(eventType);
        if (observersByType != null)
            observersByType.stream()
                    .peek((x)->log.info("Observer: {}", x))
                    .parallel()
                    .forEach(x->x.notify(event, eventType));
    }
}
