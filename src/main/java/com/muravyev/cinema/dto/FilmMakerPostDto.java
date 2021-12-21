package com.muravyev.cinema.dto;

import lombok.Data;

@Data
public class FilmMakerPostDto {
    private long film;
    private long maker;
    private String post;
}
