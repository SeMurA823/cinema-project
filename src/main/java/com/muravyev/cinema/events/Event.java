package com.muravyev.cinema.events;

import java.util.Map;

public interface Event<T> {
    T getValue();

    default Map<Long, String> reportInfo() {
        return Map.of();
    }
}
