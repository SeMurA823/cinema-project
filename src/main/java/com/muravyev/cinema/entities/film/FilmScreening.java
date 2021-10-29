package com.muravyev.cinema.entities.film;

import com.muravyev.cinema.entities.BaseEntity;
import com.muravyev.cinema.entities.hall.Hall;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "film_screenings")
public class FilmScreening extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @Column(name = "date")
    private Date date;
}
