package com.muravyev.cinema.entities.session;

import com.muravyev.cinema.entities.IdentityBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "refresh_tokens")
public class RefreshToken extends IdentityBaseEntity {
    @Column(name = "token")
    private String token;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private ClientSession clientSession;
}
