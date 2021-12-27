package com.muravyev.cinema.entities.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.IdentityBaseEntity;
import com.muravyev.cinema.entities.hall.Seat;
import com.muravyev.cinema.entities.screening.FilmScreening;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "tickets")
public class Ticket extends IdentityBaseEntity {
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    private BigDecimal price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "film_screening_id", nullable = false)
    private FilmScreening filmScreening;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    private TicketRefund ticketRefund;

    @Transient
    private boolean isExpired;

    @PostLoad
    private void checkExpired() {
        Date now = new Date();
        isExpired = filmScreening.getDate().before(now);
    }
}
