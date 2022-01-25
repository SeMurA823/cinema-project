package com.muravyev.cinema.security.exceptions;

public class IllegalSessionException extends IllegalArgumentException {
    public IllegalSessionException() {
        super("Illegal session");
    }

    public IllegalSessionException(Throwable cause) {
        super("Illegal session", cause);
    }
}
