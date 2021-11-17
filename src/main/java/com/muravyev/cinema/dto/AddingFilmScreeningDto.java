package com.muravyev.cinema.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AddingFilmScreeningDto {
    @Positive
    private long hallId;
    @Positive
    private long filmId;
    @PositiveOrZero
    private BigDecimal price;
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm z")
    private Date date;
}
