package com.muravyev.cinema.security.services.cookieConfigurator;

public interface CookieConfigurator<T> {


    String configureSession(T t);

    String configure(T t);
}
