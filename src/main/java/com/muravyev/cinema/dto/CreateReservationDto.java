package com.muravyev.cinema.dto;

import lombok.Data;

@Data
public class CreateReservationDto {
    private long filmScreening;
    private String num;
    private String row;
}
