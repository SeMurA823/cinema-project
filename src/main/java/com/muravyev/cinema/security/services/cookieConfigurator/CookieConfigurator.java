package com.muravyev.cinema.security.services.cookieConfigurator;

public interface CookieConfigurator<T> {


    String configureSessionCookie(T t);

    String configure(T t);
}
