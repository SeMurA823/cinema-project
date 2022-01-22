package com.muravyev.cinema.security.services.token;

import java.util.Date;
import java.util.Map;

public interface Token {
    String compact();
    String getSubject();
    Date getExpirationDate();
    Map<String, Object> result();
}
