package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.RegistrationDto;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;

public interface CustomerService {

    Customer registration(RegistrationDto registrationForm, User user);

    Customer of(RegistrationDto registrationForm);

    Customer getCustomer(User user);
}
