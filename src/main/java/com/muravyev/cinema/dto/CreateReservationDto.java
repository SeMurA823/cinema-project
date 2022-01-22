package com.muravyev.cinema.dto;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class CreateReservationDto {
    @Positive
    private int num;
    @Positive
    private int row;
}
