package com.animeapi.animeapi.payload;

import com.animeapi.animeapi.ressources.GenreAnime;
import com.animeapi.animeapi.ressources.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimePayload {
    @JsonProperty("mal_id")
    public int id;
    @JsonProperty("url")
    public String url;
    @JsonProperty("title")
    public String titleName;
    @JsonProperty("title_japanese")
    public String titleJapanese;
    @JsonProperty("score")
    public double score;
    @JsonProperty("type")
    public String Type;
    @JsonProperty("year")
    public int date;
    @JsonProperty("images")
    public Image image;
    @JsonProperty("episodes")
    public int NumberEpisode;
    @JsonProperty("rank")
    public int Rank;
    @JsonProperty("season")
    public String Season;

    @JsonProperty("genres")
    public List<GenreAnime> Genres;
}
