package com.animeapi.animeapi.payload;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)

public class AnimeCharacterEntry {
    @JsonProperty("character")
    public Character Character;
    @JsonProperty("role")
    public String Role;
    @JsonProperty("voice_actors")
    public List<VoiceActor> voiceActors;
}
