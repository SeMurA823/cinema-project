package com.muravyev.cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PosterDto {
    private long filmId;
    @JsonProperty("file")
    private String fileBase64;
}
