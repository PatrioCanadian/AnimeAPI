package com.animeapi.animeapi.payload;

import com.animeapi.animeapi.ressources.Image;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)

public class Person {
    @JsonProperty("mal_id")
    public int id;
    @JsonProperty("url")
    public String Url;
    @JsonProperty("images")
    public Image image;
    @JsonProperty("name")
    public String Name;
}
