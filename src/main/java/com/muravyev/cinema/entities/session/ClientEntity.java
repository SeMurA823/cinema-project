package com.muravyev.cinema.entities.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.BaseEntity;
import com.muravyev.cinema.entities.users.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.UUID;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "client_sessions")
public class ClientEntity extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "client_ipv4")
    private String ipv4;

    @Column(name = "client_ipv6")
    private String ipv6;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
