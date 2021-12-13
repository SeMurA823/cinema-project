package com.muravyev.cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FilmDto {
    private long id;
    private String name;
    private Date localPremiere;
    private Date worldPremiere;
    private String plot;
    private List<String> countriesId;
    private String ageLimitId;
    @JsonProperty(value = "isActive", required = true)
    private boolean isActive;
}
