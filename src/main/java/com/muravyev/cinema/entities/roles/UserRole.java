package com.muravyev.cinema.entities.roles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.IdentityBaseEntity;
import com.muravyev.cinema.entities.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_roles")
public class UserRole extends IdentityBaseEntity {
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
