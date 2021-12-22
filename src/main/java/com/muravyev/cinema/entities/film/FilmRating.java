package com.muravyev.cinema.entities.film;

import com.muravyev.cinema.entities.IdentityBaseEntity;
import com.muravyev.cinema.entities.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "film_ratings")
public class FilmRating extends IdentityBaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    private int rating;
}
