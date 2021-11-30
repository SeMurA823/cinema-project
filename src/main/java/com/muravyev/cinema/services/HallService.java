package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.entities.hall.Seat;

public interface HallService {
    Hall setHall(String name);

    void disableHall(long hallId);

    Seat addSeat(long hallId, int num, int row);

    void setUnUsedSeat(long hallId, int num, int row, boolean unused);
}
