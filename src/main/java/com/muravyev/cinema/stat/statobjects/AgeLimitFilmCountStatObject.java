package com.muravyev.cinema.stat.statobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AgeLimitFilmCountStatObject {
    private String limit;
    private long count;
}
