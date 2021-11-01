package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.RegistrationDto;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.CustomerRepository;
import com.muravyev.cinema.services.CustomerService;
import org.springframework.stereotype.Service;

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
        Customer customer = new Customer();
        customer.setFirstName(registrationForm.getFirstName());
        customer.setLastName(registrationForm.getLastName());
        customer.setPatronymic(registrationForm.getPatronymic());
        customer.setBirthDate(registrationForm.getBirthDate());
        customer.setSex(registrationForm.getSex());
        return customer;
    }
}
