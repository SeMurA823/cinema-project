package com.muravyev.cinema.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class IdentityBaseEntity extends BaseEntity {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public IdentityBaseEntity() {
    }
}
