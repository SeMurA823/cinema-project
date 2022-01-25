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

    Seat createSeat(long hallId, int row);

    List<Seat> createSeats(long hallId, int row, int size);

    void setUnUsedStatusSeat(long hallId, int num, int row, boolean unused);

    Page<Hall> getAllHalls(Pageable pageable);

    Hall getHall(long hallId);

    Map<Integer, List<Seat>> getAllSeats(long hallId);

    void setUnUsedStatusSeats(long hallId, List<Long> seatIds, boolean b);

    void deleteSeats(long hallId, List<Long> seatIds);

    Page<Hall> getActiveHalls(String search, Pageable pageable);
}
