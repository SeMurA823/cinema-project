package com.muravyev.cinema.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "refresh_tokens")
public class RefreshToken extends BaseEntity {
    @Column(name = "token")
    private String token;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
