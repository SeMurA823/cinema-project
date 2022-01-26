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
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        if (!super.equals(o)) return false;
        Reservation that = (Reservation) o;
        return isExpired == that.isExpired
                && Objects.equals(user, that.user)
                && Objects.equals(seat, that.seat)
                && Objects.equals(expiryDate, that.expiryDate)
                && Objects.equals(filmScreening, that.filmScreening);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, seat, expiryDate, filmScreening, isExpired);
    }
}
