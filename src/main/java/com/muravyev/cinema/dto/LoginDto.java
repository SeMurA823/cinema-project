package com.muravyev.cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
    @JsonProperty("rememberMe")
    private boolean rememberMe;
}
