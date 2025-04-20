package com.animeapi.animeapi.ressources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Imagejpg {
    @JsonProperty("image_url")
    public String ImageURL;
    @JsonProperty("small_image_url")
    public String SmallImageURL;
    @JsonProperty("large_image_url")
    public String LargeImageURL;
}
