package com.muravyev.cinema.entities.screening;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;

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

    @Column(name = "status_seat")
    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;
}
