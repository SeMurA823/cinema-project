package com.muravyev.cinema.dto;

import lombok.Data;

@Data
public class SeatDto {
    private long hallId;
    private int row;
    private int number;
}
