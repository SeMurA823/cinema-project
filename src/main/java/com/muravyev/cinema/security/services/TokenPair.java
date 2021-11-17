package com.muravyev.cinema.security.services;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Date;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
public interface TokenPair {
    @JsonGetter("access_token")
    String getAccessToken();
    @JsonGetter("refresh_token")
    String getRefreshToken();
    @JsonGetter("expires_in")
    Date getExpiryDate();
}