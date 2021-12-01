package com.muravyev.cinema.entities.film;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "countries")
public class Country extends BaseEntity implements Persistable<String> {

    @Id
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

    @JsonIgnore
    @Override
    public String getId() {
        return code;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return getCreated() == null;
    }
}
