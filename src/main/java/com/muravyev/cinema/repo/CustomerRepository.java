package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUserAndEntityStatus(User user, EntityStatus entityStatus);
}
