package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.HallDto;
import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.entities.hall.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HallService {
    Hall editHall(long hallId, HallDto hallDto);

    Hall createHall(HallDto hallDto);

    void disableHall(long hallId);

    Seat addSeat(long hallId, int num, int row);

    List<Seat> addSeats(long hallId, int row, int size);

    void setUnUsedSeat(long hallId, int num, int row, boolean unused);

    Page<Hall> getAllHalls(Pageable pageable);

    Hall getHall(long hallId);
}
