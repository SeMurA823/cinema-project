package com.muravyev.cinema.entities.payment;

import com.muravyev.cinema.entities.BaseEntity;
import com.muravyev.cinema.entities.hall.Place;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.entities.film.FilmScreening;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne
    @JoinColumn(name="film_screening_id", nullable = false)
    private FilmScreening filmScreening;
}
