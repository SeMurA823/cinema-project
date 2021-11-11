package com.muravyev.cinema.entities.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.BaseEntity;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.hall.Place;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "reservations")
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @ManyToOne
    @JoinColumn(name="film_screening_id", nullable = false)
    private FilmScreening filmScreening;

    @Transient
    private boolean isExpired;

    @PostLoad
    private void checkExpired() {
        Date now = new Date();
        isExpired = expiryDate.before(now) || filmScreening.getDate().before(now);
    }
}
