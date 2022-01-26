package com.muravyev.cinema.security.services.token;

import java.util.Date;
import java.util.Map;

public interface Token {
    /**
     * Packs the token into a string
     *
     * @return compact string
     */
    String compact();

    /**
     * Authentication subject
     *
     * @return subject
     */
    String getSubject();

    /**
     * Token expiration date
     * (Date the token will expire)
     *
     * @return expiration date
     */
    Date getExpirationDate();

    /**
     * Packing token then map object
     *
     * @return string - key, object - value
     */
    Map<String, Object> result();
}
