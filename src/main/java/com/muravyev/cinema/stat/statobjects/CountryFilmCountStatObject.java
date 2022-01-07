package com.muravyev.cinema.stat.statobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountryFilmCountStatObject {
    private String country;
    private long count;
}
