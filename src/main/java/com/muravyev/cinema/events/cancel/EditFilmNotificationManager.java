package com.muravyev.cinema.events.cancel;

import com.muravyev.cinema.events.NotificationManager;
import com.muravyev.cinema.events.Observer;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class EditFilmNotificationManager implements NotificationManager<EditFilmEvent> {

    private final Set<Observer<EditFilmEvent>> observers  = new HashSet<>();

    @Override
    public void subscribe(Observer<EditFilmEvent> observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer<EditFilmEvent> observer) {
        observers.remove(observer);
    }

    @Override
    public void notify(EditFilmEvent event) {
        observers.stream()
                .parallel()
                .forEach(x->x.notify(event));
    }
}
