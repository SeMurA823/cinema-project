package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.entityStatus = 'ENABLE' AND c.user = :user")
    Optional<Customer> findByUser(@Param("user") User user);
}
