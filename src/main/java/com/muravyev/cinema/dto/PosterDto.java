package com.muravyev.cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
public class PosterDto {
    @Positive
    private long filmId;
    @NotEmpty
    @JsonProperty("file")
    private String fileBase64;
}
