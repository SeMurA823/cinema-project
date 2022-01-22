package com.muravyev.cinema.entities.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.IdentityBaseEntity;
import com.muravyev.cinema.entities.hall.Seat;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservations")
public class Reservation extends IdentityBaseEntity {
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Seat seat;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @ManyToOne
    @JoinColumn(name = "film_screening_id", nullable = false)
    private FilmScreening filmScreening;

    @Transient
    private boolean isExpired;

    @PostLoad
    private void checkExpired() {
        Date now = new Date();
        isExpired = expiryDate.before(now) || filmScreening.getDate().before(now);
    }
}
