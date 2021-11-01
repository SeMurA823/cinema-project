package com.muravyev.cinema.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RegistrationDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String sex;
    private Date birthDate;
}
