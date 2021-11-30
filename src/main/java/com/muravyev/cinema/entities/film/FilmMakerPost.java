package com.muravyev.cinema.entities.film;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.IdentityBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "filmmaker_posts")
public class FilmMakerPost extends IdentityBaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "filmmaker_id")
    private FilmMaker filmMaker;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
}
