package com.muravyev.cinema.entities.payment;

import com.muravyev.cinema.entities.BaseEntity;
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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="film_screening_id", nullable = false)
    private FilmScreening filmScreening;
}
