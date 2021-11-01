package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
