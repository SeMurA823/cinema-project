package com.muravyev.cinema.entities.session;

import com.muravyev.cinema.entities.IdentityBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Table(name = "refresh_tokens")
public class RefreshTokenEntity extends IdentityBaseEntity {
    @Column(name = "token")
    private String token;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RefreshTokenEntity)) return false;
        if (!super.equals(o)) return false;
        RefreshTokenEntity that = (RefreshTokenEntity) o;
        return Objects.equals(token, that.token) && Objects.equals(expiryDate, that.expiryDate) && Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), token, expiryDate, client);
    }
}
