package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.hall.Seat;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.payment.Reservation;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.CustomerRepository;
import com.muravyev.cinema.repo.FilmScreeningRepository;
import com.muravyev.cinema.repo.SeatRepository;
import com.muravyev.cinema.repo.ReservationRepository;
import com.muravyev.cinema.services.ReservationService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.Date;

@Service
@Log4j2
public class ReservationServiceImpl implements ReservationService {
    @Value("${app.reservation.expired}")
    private int reserveMillis;

    private ReservationRepository reservationRepository;
    private CustomerRepository customerRepository;
    private FilmScreeningRepository screeningRepository;
    private SeatRepository seatRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  CustomerRepository customerRepository,
                                  FilmScreeningRepository screeningRepository,
                                  SeatRepository seatRepository) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.screeningRepository = screeningRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Reservation createReservation(FilmScreening filmScreening, User user, Seat seat) {
        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(EntityNotFoundException::new);
        return createReservation(filmScreening, customer, seat);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Reservation createReservation(long filmScreeningId, int row, int cell, User user) {
        FilmScreening filmScreening = screeningRepository.findById(filmScreeningId)
                .orElseThrow(EntityNotFoundException::new);
        Seat seat = seatRepository.findByRowAndNumberAndHall(row, cell, filmScreening.getHall())
                .orElseThrow(EntityNotFoundException::new);
        return createReservation(filmScreening, user, seat);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Reservation createReservation(FilmScreening filmScreening,
                                         Customer customer,
                                         Seat seat) {
        if (reservationRepository.existsByFilmScreeningAndPlaceAndExpiryDateBefore(seat, filmScreening)) {
            log.log(Level.DEBUG, "Reservation exists date: {}", filmScreening.getDate());
            log.log(Level.DEBUG, "  row = {}", seat.getRow());
            log.log(Level.DEBUG, "  num = {}", seat.getNumber());
            throw new EntityExistsException();
        }
        Reservation reservation = new Reservation();
        reservation.setSeat(seat);
        reservation.setExpiryDate(getExpiredDate());
        reservation.setFilmScreening(filmScreening);
        reservation.setCustomer(customer);
        return reservationRepository.save(reservation);
    }

    @Override
    public Page<Reservation> getReservations(User user, Pageable pageable) {
        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(EntityNotFoundException::new);
        return getReservations(customer, pageable);
    }

    @Override
    public Page<Reservation> getReservations(Customer customer, Pageable pageable) {
        return reservationRepository.findAllActualByCustomer(customer, pageable);
    }

    @Override
    public void cancelReservation(Reservation reservation) {
        reservation.setEntityStatus(EntityStatus.DISABLE);
        reservation.setDisableDate(new Date());
        reservationRepository.save(reservation);
    }

    @Override
    public void cancelReservation(Customer customer, long reservationId) {
        Reservation reservation = reservationRepository.findByIdAndCustomer(reservationId, customer)
                .orElseThrow(EntityNotFoundException::new);
        cancelReservation(reservation);
    }

    @Override
    public void cancelReservation(User user, long reservationId) {
        Reservation reservation = reservationRepository.findByIdAndCustomerUser(reservationId, user)
                .orElseThrow(EntityNotFoundException::new);
        cancelReservation(reservation);
    }

    private Date getExpiredDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        log.log(Level.DEBUG, "Start date : {}", calendar.getTime());
        calendar.add(Calendar.MILLISECOND, reserveMillis);
        log.log(Level.DEBUG, "Expiry date : {}", calendar.getTime());
        return calendar.getTime();
    }
}
