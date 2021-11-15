package com.muravyev.cinema.entities.payment;

import com.muravyev.cinema.entities.BaseEntity;
import com.muravyev.cinema.entities.hall.Seat;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.screening.FilmScreening;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name="film_screening_id", nullable = false)
    private FilmScreening filmScreening;

    @Transient
    private boolean isExpired;

    @PostLoad
    private void checkExpired(){
        Date now  = new Date();
        isExpired = filmScreening.getDate().before(now);
    }
}
