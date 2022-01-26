package com.muravyev.cinema.entities.film;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "age_limits")
public class AgeLimit extends BaseEntity implements Persistable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_age", nullable = false)
    private int startAge;

    @JsonIgnore
    @OneToMany(mappedBy = "ageLimit")
    private List<Film> films;

    @Override
    public String getId() {
        return name;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return this.getCreated() == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AgeLimit ageLimit = (AgeLimit) o;
        return startAge == ageLimit.startAge && Objects.equals(name, ageLimit.name)
                && Objects.equals(description, ageLimit.description) && Objects.equals(films, ageLimit.films);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, startAge, films);
    }
}
