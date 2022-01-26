package com.muravyev.cinema.entities.screening;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Immutable
@Subselect("select * from screening_seats")
@Getter
public class FilmScreeningSeat {
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "number")
    private long number;

    @Column(name = "row")
    private long row;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "screening_id")
    private FilmScreening screening;

    @JsonProperty("status")
    @Column(name = "status_seat")
    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmScreeningSeat)) return false;
        FilmScreeningSeat that = (FilmScreeningSeat) o;
        return number == that.number && row == that.row
                && Objects.equals(id, that.id)
                && Objects.equals(screening, that.screening)
                && seatStatus == that.seatStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, row, screening, seatStatus);
    }
}
