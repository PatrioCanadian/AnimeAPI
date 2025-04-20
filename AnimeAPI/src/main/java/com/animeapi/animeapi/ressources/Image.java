package com.animeapi.animeapi.ressources;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)

public class Image {
    @JsonProperty("jpg")
    public Imagejpg Pané;
}
