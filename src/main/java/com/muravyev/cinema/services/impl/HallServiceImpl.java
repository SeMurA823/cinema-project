package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.entities.hall.Seat;
import com.muravyev.cinema.repo.HallRepository;
import com.muravyev.cinema.repo.SeatRepository;
import com.muravyev.cinema.services.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class HallServiceImpl implements HallService {
    private SeatRepository seatRepository;
    private HallRepository hallRepository;

    @Autowired
    public void setSeatRepository(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Autowired
    public void setHallRepository(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public Hall setHall(String name) {
        Hall hall = hallRepository.findByNameAndEntityStatus(name, EntityStatus.ACTIVE)
                .orElse(new Hall());
        hall.setName(name);
        return hallRepository.save(hall);
    }

    @Override
    public void disableHall(long hallId) {
        hallRepository.findById(hallId)
                .ifPresent(hall -> {
                    hall.setEntityStatus(EntityStatus.NOT_ACTIVE);
                    hallRepository.save(hall);
                });
    }

    @Override
    public Seat addSeat(long hallId, int num, int row) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(EntityNotFoundException::new);
        Seat seat = new Seat();
        seat.setHall(hall);
        seat.setNumber(num);
        seat.setRow(row);
        return seatRepository.save(seat);
    }

    @Override
    public List<Seat> addSeats(long hallId, int row, int size) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(EntityNotFoundException::new);
        List<Seat> collect = IntStream.rangeClosed(1, size)
                .mapToObj(this::createSeat)
                .peek(seat -> seat.setRow(row))
                .peek(seat -> seat.setHall(hall))
                .peek(seat -> seat.setUnUsed(false))
                .collect(Collectors.toList());
        return seatRepository.saveAll(collect);
    }

    @Override
    public void setUnUsedSeat(long hallId, int num, int row, boolean unused) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(EntityNotFoundException::new);
        seatRepository.findByRowAndNumberAndHall(row, num, hall)
                .ifPresent(seat -> {
                    seat.setUnUsed(unused);
                    seatRepository.save(seat);
                });
    }

    @Override
    public Page<Hall> getAllHalls(Pageable pageable) {
        return hallRepository.findAll(pageable);
    }

    private Seat createSeat(int number) {
        Seat seat = new Seat();
        seat.setNumber(number);
        return seat;
    }

}
