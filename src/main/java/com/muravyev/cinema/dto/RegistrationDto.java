package com.muravyev.cinema.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class RegistrationDto {
    @Size(min = 6)
    private String username;
    @Size(min = 6)
    private String password;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private String patronymic;
    private String gender;
    @Past
    private Date birthDate;
}
