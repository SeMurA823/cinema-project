package com.muravyev.cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("firstName")
    private String firstName;
    @NotEmpty
    @JsonProperty("lastName")
    private String lastName;
    private String patronymic;
    private String gender;
    @Past
    @JsonProperty("birthDate")
    private Date birthDate;
}
