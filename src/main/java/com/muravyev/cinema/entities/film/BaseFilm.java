package com.muravyev.cinema.entities.film;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.IdentityBaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseFilm extends IdentityBaseEntity {
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

    @JsonIgnore
    @OneToMany(mappedBy = "film")
    private Set<FilmMark> marks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseFilm)) return false;
        if (!super.equals(o)) return false;
        BaseFilm baseFilm = (BaseFilm) o;
        return duration == baseFilm.duration
                && Objects.equals(name, baseFilm.name)
                && Objects.equals(localPremiere, baseFilm.localPremiere)
                && Objects.equals(worldPremiere, baseFilm.worldPremiere)
                && Objects.equals(plot, baseFilm.plot)
                && Objects.equals(countries, baseFilm.countries)
                && Objects.equals(ageLimit, baseFilm.ageLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, localPremiere, worldPremiere, duration, plot, countries, ageLimit);
    }
}
