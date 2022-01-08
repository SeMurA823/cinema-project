package com.muravyev.cinema.dto;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class SeatDto {
    @Positive
    private long hallId;
    @Positive
    private int row;
    @Positive
    private int number;
}
