package com.muravyev.cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CountryDto {
    @NotEmpty
    private String id;
    @NotEmpty
    @JsonProperty("fullName")
    private String fullName;
    @NotEmpty
    @JsonProperty("shortName")
    private String shortName;
}
