package com.muravyev.cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NotificationFormDto {
    @JsonProperty(required = true)
    private long user;

    @JsonProperty(required = true)
    private String message;
}
