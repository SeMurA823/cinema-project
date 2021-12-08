package com.muravyev.cinema.entities.film;

import com.muravyev.cinema.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "age_limits")
public class AgeLimit extends BaseEntity implements Persistable<String> {
    @Id
    @Column(name = "name")
    private String id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_age", nullable = false)
    private int startAge;

    @Column(name = "value")
    private String value;

    @Override
    public boolean isNew() {
        return this.getCreated() == null;
    }
}
