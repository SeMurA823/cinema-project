package com.muravyev.cinema.entities.film;

import com.muravyev.cinema.entities.IdentityBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "countries")
public class Country extends IdentityBaseEntity {
    @Column(name = "code")
    private String code;

    @Column(name = "full_name", unique = true, nullable = false)
    private String fullName;

    @Column(name = "short_name", unique = true, nullable = false)
    private String shortName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(code, country.code) && Objects.equals(fullName, country.fullName) && Objects.equals(shortName, country.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, fullName, shortName);
    }
}
