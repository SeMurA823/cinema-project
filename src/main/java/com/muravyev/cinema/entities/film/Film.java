package com.muravyev.cinema.entities.film;

import com.muravyev.cinema.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "films")
public class Film extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date_local_premiere")
    private Date localPremiere;

    @Column(name = "date_world_premiere")
    private Date worldPremiere;

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
    private Set<Country> countries;

    @ManyToOne
    @JoinColumn(name = "age_limit_id")
    private AgeLimit ageLimit;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "film_id")
    private Set<FilmPoster> posters;
}
