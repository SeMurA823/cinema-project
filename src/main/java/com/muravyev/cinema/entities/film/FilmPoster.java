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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmPoster that = (FilmPoster) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(filename, that.filename) && Objects.equals(film, that.film);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), filename);
    }

    @Override
    public boolean isActive() {
        return super.isActive();
    }
}
