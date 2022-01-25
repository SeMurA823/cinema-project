package com.muravyev.cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FilmDto {
    private long id;
    @NotEmpty
    private String name;
    @NotNull
    private Date localPremiere;
    @NotNull
    private Date worldPremiere;
    @NotEmpty
    private String plot;
    @NotEmpty
    private List<String> countriesId;
    private String ageLimitId;
    @JsonProperty(value = "isActive", required = true)
    private boolean isActive;
    private int duration;
}
