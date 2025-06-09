package com.animeapi.animeapi.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)

public class VoiceActor {
    @JsonProperty("person")
    public Person Person;
    @JsonProperty("language")
    public String Language;
}
