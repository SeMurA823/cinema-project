package com.muravyev.cinema.stat.statobjects;

import com.muravyev.cinema.entities.film.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountryFilmCountStatObject {
    private String country;
    private long count;
}
