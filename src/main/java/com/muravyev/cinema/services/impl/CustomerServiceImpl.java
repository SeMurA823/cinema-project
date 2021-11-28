package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.RegistrationDto;
import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.CustomerRepository;
import com.muravyev.cinema.services.CustomerService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer registration(RegistrationDto registrationForm, User user) {
        Customer customer = of(registrationForm);
        customer.setUser(user);
        return customerRepository.save(customer);
    }

    @Override
    public Customer of(RegistrationDto registrationForm) {
        return new Customer();
    }

    @Override
    public Customer getCustomer(User user) {
        return customerRepository.findByUserAndEntityStatus(user, EntityStatus.ACTIVE)
                .orElseThrow(EntityNotFoundException::new);

    }
}
