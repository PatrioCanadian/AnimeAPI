package com.animeapi.animeapi.ressources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginationInfo {
    @JsonProperty("last_visible_page")
    public int TotalPages;
}

