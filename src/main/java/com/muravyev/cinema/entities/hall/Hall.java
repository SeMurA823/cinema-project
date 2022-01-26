package com.muravyev.cinema.entities.hall;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.IdentityBaseEntity;
import com.muravyev.cinema.entities.screening.FilmScreening;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "halls")
public class Hall extends IdentityBaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "hall")
    private List<Seat> seats;

    @JsonIgnore
    @OneToMany(mappedBy = "hall")
    private List<FilmScreening> screeningList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hall)) return false;
        if (!super.equals(o)) return false;
        Hall hall = (Hall) o;
        return Objects.equals(name, hall.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
