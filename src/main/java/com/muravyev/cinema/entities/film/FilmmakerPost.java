package com.muravyev.cinema.entities.film;

import com.muravyev.cinema.entities.IdentityBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "filmmaker_posts")
public class FilmmakerPost extends IdentityBaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "filmmaker_id")
    private Filmmaker filmmaker;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
}
