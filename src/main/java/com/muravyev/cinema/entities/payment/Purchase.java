package com.muravyev.cinema.entities.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.muravyev.cinema.entities.IdentityBaseEntity;
import com.muravyev.cinema.entities.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "purchases")
public class Purchase extends IdentityBaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "purchase", cascade = {CascadeType.ALL})
    private List<Ticket> tickets;

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + getId() +
                "}";
    }

    @JsonProperty("created")
    @Override
    public Date getCreated() {
        return super.getCreated();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchase)) return false;
        if (!super.equals(o)) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(user, purchase.user)
                && Objects.equals(tickets, purchase.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, tickets);
    }
}
