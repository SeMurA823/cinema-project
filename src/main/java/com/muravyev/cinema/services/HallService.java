package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.HallDto;
import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.entities.hall.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface HallService {
    Hall editHall(long hallId, HallDto hallDto);

    Hall editStatus(long hallId, EntityStatus status);

    Hall createHall(HallDto hallDto);

    void disableHall(long hallId);

    Seat addSeat(long hallId, int row);

    List<Seat> addSeats(long hallId, int row, int size);

    void setUnUsedSeat(long hallId, int num, int row, boolean unused);

    Page<Hall> getAllHalls(Pageable pageable);

    Hall getHall(long hallId);

    Map<Integer, List<Seat>> getAllSeats(long hallId);

    void setUnUsedSeats(long hallId, List<Long> seatIds, boolean b);

    void deleteSeats(long hallId, List<Long> seatIds);

    Page<Hall> getHalls(String search, Pageable pageable);
}
