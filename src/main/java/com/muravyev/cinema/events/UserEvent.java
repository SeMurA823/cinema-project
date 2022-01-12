package com.muravyev.cinema.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public abstract class UserEvent<T> implements Event<T>{
    private final Map<Long, String> messages;

    @Override
    public Map<Long, String> reportInfo() {
        return new HashMap<>(messages);
    }
}
