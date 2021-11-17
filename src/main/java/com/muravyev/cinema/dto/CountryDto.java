package com.muravyev.cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CountryDto {
    @NotEmpty
    private String code;
    @NotEmpty
    @JsonProperty("full_name")
    private String fullName;
    @NotEmpty
    @JsonProperty("short_name")
    private String shortName;
}
