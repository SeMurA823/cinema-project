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
@Table(name = "film_posters")
public class FilmPoster extends IdentityBaseEntity {
    @Column(name = "filename", unique = true)
    private String filename;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private Film film;

    @Override
    public boolean isActive() {
        return super.isActive();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmPoster)) return false;
        if (!super.equals(o)) return false;
        FilmPoster that = (FilmPoster) o;
        return Objects.equals(filename, that.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), filename);
    }
}
