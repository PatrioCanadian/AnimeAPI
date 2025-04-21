package com.animeapi.animeapi.payload;

import com.animeapi.animeapi.ressources.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)

public class Character {
    @JsonProperty("mal_id")
    public int id;
    @JsonProperty("url")
    public String URL;
    @JsonProperty("images")
    public Image image;
    @JsonProperty("name")
    public String CharacterName;
}
