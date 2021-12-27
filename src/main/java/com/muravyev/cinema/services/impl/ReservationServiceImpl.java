package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.ReservationDto;
import com.muravyev.cinema.dto.SeatDto;
import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.entities.hall.Seat;
import com.muravyev.cinema.entities.payment.Reservation;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.FilmScreeningRepository;
import com.muravyev.cinema.repo.ReservationRepository;
import com.muravyev.cinema.repo.SeatRepository;
import com.muravyev.cinema.services.ReservationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ReservationServiceImpl implements ReservationService {

    @Value("${app.reserve.expiration}")
    private long reserveExpiration;

    private final ReservationRepository reservationRepository;
    private final FilmScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  FilmScreeningRepository screeningRepository,
                                  SeatRepository seatRepository) {
        this.reservationRepository = reservationRepository;
        this.screeningRepository = screeningRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    @Transactional
    public List<Reservation> createReservations(List<ReservationDto> reservationDtos, User user) {
        List<Reservation> reservations = reservationDtos.stream()
                .map(reservationDto -> toReservation(reservationDto, user))
                .collect(Collectors.toList());
        try {
            return reservationRepository.saveAll(reservations);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reservation> getReservations(long screeningId, User user) {
        return reservationRepository
                .findAllByFilmScreeningIdAndUserAndEntityStatusAndExpiryDateAfterOrderByCreatedDesc(screeningId,
                        user,
                        EntityStatus.ACTIVE,
                        new Date());
    }

    @Override
    public List<FilmScreening> getFilmScreeningWhereMyBookings(long filmId, User user) {
        return reservationRepository.findAllByUserAndFilmScreeningFilmIdAndEntityStatus(
                user,
                filmId,
                EntityStatus.ACTIVE
        );
    }

    private Reservation toReservation(ReservationDto reservationDto, User user) {
        log.info("ReservationDTO ----in---> {}", reservationDto);
        Reservation reservation = new Reservation();
        SeatDto seatDto = reservationDto.getSeat();
        FilmScreening filmScreening = screeningRepository.findByIdAndEntityStatus(reservationDto.getScreeningId(), EntityStatus.ACTIVE)
                .orElseThrow(EntityNotFoundException::new);
        reservation.setFilmScreening(filmScreening);
        Hall hall = filmScreening.getHall();
        log.info("Hall {}", hall.getId());
        Seat seat = seatRepository.findByRowAndNumberAndHallIdAndEntityStatus(seatDto.getRow(),
                        seatDto.getNumber(),
                        hall.getId(),
                        EntityStatus.ACTIVE)
                .orElseThrow(EntityNotFoundException::new);
        reservation.setSeat(seat);
        Date autoExpiryDate = createExpiryDate();
        reservation.setExpiryDate(autoExpiryDate.before(filmScreening.getDate())
                ? autoExpiryDate
                : filmScreening.getDate());
        reservation.setUser(user);
        return reservation;
    }


    private Date createExpiryDate() {
        Date now = new Date();
        return new Date(now.getTime() + reserveExpiration);
    }


}
