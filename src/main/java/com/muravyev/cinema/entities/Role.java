package com.muravyev.cinema.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


public enum Role implements GrantedAuthority {
    ADMIN,
    CUSTOMER,
    EMPLOYEE;

    @Override
    public String getAuthority() {
        return this.name();
    }

}
