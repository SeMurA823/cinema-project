package com.muravyev.cinema.entities.users;

import com.muravyev.cinema.entities.IdentityBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "customers")
public class Customer extends IdentityBaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
