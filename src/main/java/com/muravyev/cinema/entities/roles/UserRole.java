package com.muravyev.cinema.entities.roles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.BaseEntity;
import com.muravyev.cinema.entities.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_roles")
public class UserRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    private Role role;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
