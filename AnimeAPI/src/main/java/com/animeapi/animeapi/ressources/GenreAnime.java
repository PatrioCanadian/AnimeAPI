package com.animeapi.animeapi.ressources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenreAnime {
    @JsonProperty("mal_id")

    public int Id;
    @JsonProperty("name")
    public String Name;
}
