package com.muravyev.cinema.events;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class UserEvent<T> implements Event<T> {
    private final Map<Long, String> messages;

    public UserEvent(Map<Long, String> messages) {
        this.messages = messages;
    }

    @Override
    public Map<Long, String> reportInfo() {
        return new HashMap<>(messages);
    }
}
