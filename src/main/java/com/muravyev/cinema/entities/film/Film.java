package com.muravyev.cinema.entities.film;

import com.muravyev.cinema.entities.IdentityBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "films")
public class Film extends IdentityBaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date_local_premiere")
    private Date localPremiere;

    @Column(name = "date_world_premiere")
    private Date worldPremiere;

    @Column(name = "duration")
    private int duration;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "plot", nullable = false)
    private String plot;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "country_film",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private List<Country> countries;


    @ManyToOne
    @JoinColumn(name = "age_limit_id")
    private AgeLimit ageLimit;


    @OneToMany(mappedBy = "film")
    private Set<FilmPoster> posters;

}
