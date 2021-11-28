package com.muravyev.cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class AgeLimitDto {
    @JsonProperty("startAge")
    @Min(0)
    private int startAge;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
}
