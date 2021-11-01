package com.muravyev.cinema.entities.film;

import com.muravyev.cinema.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "age_limits")
public class AgeLimit extends BaseEntity {
    @Id
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_age", nullable = false)
    private int startAge;
}
