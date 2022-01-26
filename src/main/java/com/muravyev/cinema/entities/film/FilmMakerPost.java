package com.muravyev.cinema.entities.film;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.IdentityBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmMakerPost)) return false;
        if (!super.equals(o)) return false;
        FilmMakerPost that = (FilmMakerPost) o;
        return Objects.equals(name, that.name)
                && Objects.equals(filmMaker, that.filmMaker)
                && Objects.equals(film, that.film);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, filmMaker, film);
    }
}
