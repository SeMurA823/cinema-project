package com.muravyev.cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
public class RateDto {
    @Min(value = 1)
    @Max(value = 10)
    @JsonProperty("rating")
    private int rating;
    @Positive
    @JsonProperty("filmId")
    private long filmId;
}
