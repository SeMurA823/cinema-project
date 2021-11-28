package com.muravyev.cinema.entities.roles;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
    ADMIN,
    CUSTOMER,
    EMPLOYEE;

    @Override
    public String getAuthority() {
        return this.name();
    }

}
