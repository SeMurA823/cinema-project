package com.muravyev.cinema.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AddingFilmScreeningDto {
    private long hallId;
    private long filmId;
    private Date date;
}
