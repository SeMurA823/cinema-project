package com.muravyev.cinema.entities.film;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.IdentityBaseEntity;
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
@Table(name = "filmmakers")
public class FilmMaker extends IdentityBaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @JsonIgnore
    @OneToMany(mappedBy = "filmMaker")
    private List<FilmMakerPost> postList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmMaker)) return false;
        if (!super.equals(o)) return false;
        FilmMaker filmMaker = (FilmMaker) o;
        return Objects.equals(firstName, filmMaker.firstName)
                && Objects.equals(lastName, filmMaker.lastName)
                && Objects.equals(patronymic, filmMaker.patronymic)
                && Objects.equals(postList, filmMaker.postList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, patronymic, postList);
    }
}
