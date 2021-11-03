package com.muravyev.cinema.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AddingFilmScreeningDto {
    private long hallId;
    private long filmId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm z")
    private Date date;
}
