package com.animeapi.animeapi.payload;
import com.animeapi.animeapi.ressources.PaginationInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAnimePayload {
    @JsonProperty("data")
    public List<AnimePayload> Animes;
    @JsonProperty("pagination")
    public PaginationInfo PaginationInfo;
}
