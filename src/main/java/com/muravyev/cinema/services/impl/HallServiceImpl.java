package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.HallDto;
import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.entities.hall.Seat;
import com.muravyev.cinema.repo.HallRepository;
import com.muravyev.cinema.repo.SeatRepository;
import com.muravyev.cinema.services.HallService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class HallServiceImpl implements HallService {
    private final SeatRepository seatRepository;
    private final HallRepository hallRepository;

    public HallServiceImpl(SeatRepository seatRepository, HallRepository hallRepository) {
        this.seatRepository = seatRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public Hall editHall(long hallId, HallDto hallDto) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(EntityNotFoundException::new);
        hall.setName(hallDto.getName());
        return hallRepository.save(hall);
    }

    @Override
    public Hall createHall(HallDto hallDto) {
        Hall hall = new Hall();
        hall.setName(hallDto.getName());
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Seat addSeat(long hallId, int row) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(EntityNotFoundException::new);
        Seat seat = new Seat();
        seat.setHall(hall);
        Integer number = seatRepository.findLastSeatNumberInRow(row, hall)
                .orElse(0);
        seat.setNumber(number + 1);
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
        seatRepository.findByRowAndNumberAndHallIdAndEntityStatus(row, num, hallId, EntityStatus.ACTIVE)
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

    @Override
    public Hall getHall(long hallId) {
        return hallRepository.findById(hallId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Map<Integer, List<Seat>> getAllSeats(long hallId) {
        List<Seat> seats = seatRepository.findAllByHallId(hallId, Sort.by("row").and(Sort.by("number")));
        return seats.stream()
                .collect(Collectors.groupingBy(Seat::getRow,
                        TreeMap::new,
                        Collectors.toList()));
    }

    @Override
    @Transactional
    public void setUnUsedSeats(long hallId, List<Long> seatIds, boolean unUsed) {
        seatRepository.setAllUnUsedStatus(hallId, seatIds, unUsed);
    }

    @Override
    @Transactional
    public void deleteSeats(long hallId, List<Long> seatIds) {
        seatRepository.deleteSeatsByIdInAndHallId(seatIds, hallId);
    }

    @Override
    public Page<Hall> getHalls(String search, Pageable pageable) {
        return hallRepository.findAllByNameContainsAndEntityStatus(search, EntityStatus.ACTIVE, pageable);
    }
}
