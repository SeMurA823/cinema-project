package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.entities.hall.Seat;

import java.util.List;

public interface HallService {
    Hall setHall(String name);

    void disableHall(long hallId);

    Seat addSeat(long hallId, int num, int row);

    List<Seat> addSeats(long hallId, int row, int size);

    void setUnUsedSeat(long hallId, int num, int row, boolean unused);
}
