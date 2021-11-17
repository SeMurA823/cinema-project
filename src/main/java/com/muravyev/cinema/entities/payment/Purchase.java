package com.muravyev.cinema.entities.payment;

import com.muravyev.cinema.entities.BaseEntity;
import com.muravyev.cinema.entities.users.Customer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "purchases")
public class Purchase extends BaseEntity {
    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}
