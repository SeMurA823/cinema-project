package com.muravyev.cinema.entities.film;

import com.muravyev.cinema.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

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

    @Override
    public String getId() {
        return name;
    }

    @Override
    public boolean isNew() {
        return this.getCreated() == null;
    }
}
