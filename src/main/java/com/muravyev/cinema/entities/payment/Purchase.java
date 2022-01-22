package com.muravyev.cinema.entities.payment;

import com.muravyev.cinema.entities.IdentityBaseEntity;
import com.muravyev.cinema.entities.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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
}
