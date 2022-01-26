package com.muravyev.cinema.entities.film;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.IdentityBaseEntity;
import com.muravyev.cinema.entities.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "film_marks")
@Getter
@Setter
public class FilmMark extends IdentityBaseEntity {

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    private int mark;

    public FilmMark() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmMark)) return false;
        if (!super.equals(o)) return false;
        FilmMark filmMark = (FilmMark) o;
        return mark == filmMark.mark
                && Objects.equals(user, filmMark.user)
                && Objects.equals(film, filmMark.film);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, film, mark);
    }
}
