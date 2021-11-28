package com.muravyev.cinema.entities.users;

import com.muravyev.cinema.entities.IdentityBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "employees")
public class Employee extends IdentityBaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "post", nullable = false)
    private String post;
}
