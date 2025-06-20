package com.animeapi.animeapi.payload;

import com.animeapi.animeapi.ressources.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimePayload {
    @JsonProperty("mal_id")
    public int id;
    @JsonProperty("url")
    public String url;
    @JsonProperty("title")
    public String titlename;
    @JsonProperty("score")
    public int score;
    @JsonProperty("year")
    public int date;
    @JsonProperty("images")
    public Image image;
    @JsonProperty("episodes")
    public int episodes;
    @JsonProperty("type")
    public String Type;



}
