package com.muravyev.cinema.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AddingFilmDto {
    private String name;
    private Date localPremiere;
    private Date worldPremiere;
    private String ageLimit;
    private String plot;
    private List<String> countries;
}
