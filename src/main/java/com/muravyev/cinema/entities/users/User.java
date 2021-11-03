package com.muravyev.cinema.entities.users;

import com.muravyev.cinema.entities.BaseEntity;
import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.roles.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserRole> userRoles;
    @Column(name = "hash_password")
    private String password;
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
                .map(UserRole::getRole)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
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
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getEntityStatus().equals(EntityStatus.ENABLE);
    }
}
