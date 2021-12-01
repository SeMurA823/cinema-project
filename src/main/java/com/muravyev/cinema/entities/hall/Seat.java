package com.muravyev.cinema.entities.hall;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.IdentityBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "seats")
public class Seat extends IdentityBaseEntity {
    @Column(name = "row", nullable = false)
    private int row;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "unused", nullable = false)
    private boolean unUsed = false;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;

    public Seat() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return row == seat.row && number == seat.number && unUsed == seat.unUsed && Objects.equals(getId(), seat.getId()) && Objects.equals(hall, seat.hall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), row, number, unUsed, hall);
    }
}
