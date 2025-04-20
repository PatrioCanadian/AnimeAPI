package com.animeapi.animeapi.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Anime {
    @JsonProperty("id")
    public int id;
    @JsonProperty("url")
    public String url;
    @JsonProperty("title")
    public String titlename;
    @JsonProperty("score")
    public int score;
    @JsonProperty("year")
    public int date;
}
