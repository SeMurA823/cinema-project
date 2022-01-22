package com.muravyev.cinema.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReservationDto {
    private SeatDto seat;
    private long screeningId;
}
