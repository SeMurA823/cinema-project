package com.muravyev.cinema.entities.hall;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.BaseEntity;
import com.muravyev.cinema.entities.screening.FilmScreening;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "halls")
public class Hall extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "hall")
    private Set<Seat> seats;

    @JsonIgnore
    @OneToMany(mappedBy = "hall")
    private List<FilmScreening> screeningList;

}
