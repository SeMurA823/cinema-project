package com.muravyev.cinema.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.muravyev.cinema.entities.BaseEntity;
import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.IdentityBaseEntity;
import com.muravyev.cinema.entities.roles.Role;
import com.muravyev.cinema.entities.roles.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User extends IdentityBaseEntity implements UserDetails {
    @CreatedDate
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<UserRole> userRoles;

    @JsonIgnore
    @Column(name = "hash_password")
    private String password;

    @JsonProperty("tel")
    @Column(name = "username", unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserStatus userStatus;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> authorities = userRoles.stream()
                .filter(BaseEntity::isActive)
                .map(UserRole::getRole)
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !userStatus.equals(UserStatus.EXPIRED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !userStatus.equals(UserStatus.LOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userStatus.equals(UserStatus.ACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return userStatus.equals(UserStatus.ACTIVE) && getEntityStatus().equals(EntityStatus.ACTIVE);
    }
}
