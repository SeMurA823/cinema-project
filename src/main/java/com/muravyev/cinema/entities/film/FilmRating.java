package com.muravyev.cinema.entities.film;

import com.muravyev.cinema.entities.BaseEntity;
import com.muravyev.cinema.entities.users.Customer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "film_ratings")
public class FilmRating extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    private int rating;
}
